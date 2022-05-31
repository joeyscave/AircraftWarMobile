package com.yuan.AircraftWarMobile.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.yuan.AircraftWarMobile.R;
import com.yuan.AircraftWarMobile.aircraft.AbstractAircraft;
import com.yuan.AircraftWarMobile.aircraft.BossEnemy;
import com.yuan.AircraftWarMobile.aircraft.EliteEnemy;
import com.yuan.AircraftWarMobile.aircraft.HeroAircraft;
import com.yuan.AircraftWarMobile.service.ImageManager;
import com.yuan.AircraftWarMobile.application.Main;
import com.yuan.AircraftWarMobile.basic.AbstractFlyingObject;
import com.yuan.AircraftWarMobile.bullet.BaseBullet;
import com.yuan.AircraftWarMobile.factory.aircraft.AbstractAircraftFactory;
import com.yuan.AircraftWarMobile.factory.aircraft.EliteEnemyFactory;
import com.yuan.AircraftWarMobile.factory.aircraft.MobEnemyFactory;
import com.yuan.AircraftWarMobile.observer.Subscriber;
import com.yuan.AircraftWarMobile.prop.AbstractProp;
import com.yuan.AircraftWarMobile.prop.BombProp;
import com.yuan.AircraftWarMobile.settings.Settings;
import com.yuan.AircraftWarMobile.thread.SimpleThreadfactory;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class GameSurfaceView extends SurfaceView implements
        SurfaceHolder.Callback, Runnable {

    public static final String TAG = "GameSurfaceView";
    private GameActivity gameActivity;

    /**
     * paint相关字段
     */
    int backGroundTop = 0;
    public float x = 50, y = 50;
    int screenWidth = 480, screenHeight = 800;
    private SurfaceHolder mSurfaceHolder;
    private Canvas canvas;  //绘图的画布
    private Paint mPaint;

    /**
     * 时间间隔(ms)，控制刷新频率
     */
    private int timeInterval = 40;

    private final HeroAircraft heroAircraft;
    private final List<AbstractAircraft> enemyAircrafts;
    private final List<BaseBullet> heroBullets;
    private final List<BaseBullet> enemyBullets;
    private final List<AbstractProp> props;

    private int enemyMaxNumber = 5;

    private int time = 0;
    private boolean gameOverFlag = false;
    public static boolean bgm_boss = false;

    /**
     * 周期（ms)
     * 指示子弹的发射、敌机的产生频率
     */
    private int cycleDuration = 600;
    private int cycleTime = 0;
    /**
     * 飞行器工厂，用于创建不同飞行器
     */
    AbstractAircraftFactory enemyFactory;

    public GameSurfaceView(Context context) {
        super(context);
        gameActivity = (GameActivity) context;

        // paint init
        mPaint = new Paint();  //设置画笔
        mSurfaceHolder = this.getHolder();
        mSurfaceHolder.addCallback(this);
        loadImage();
        this.setFocusable(true);

        // service init
        gameOverFlag = false;
        heroAircraft = HeroAircraft.getHeroAircraft();
        enemyAircrafts = new LinkedList<>();
        heroBullets = new LinkedList<>();
        enemyBullets = new LinkedList<>();
        props = new LinkedList<>();
    }

    /**
     * 游戏启动入口，执行游戏逻辑
     */
    public void action() {

        time += timeInterval;

        // 增大游戏难度
        if (time % 9600 == 0) Main.settings.harder();

        // 周期性执行（控制频率）
        if (timeCountAndNewCycleJudge()) {
            System.out.println(time);
            // 新敌机产生
            if (enemyAircrafts.size() < enemyMaxNumber) {
                if (Math.random() < Main.settings.eliteEnemyEmergeProb) {
                    enemyFactory = new EliteEnemyFactory();
                } else {
                    enemyFactory = new MobEnemyFactory();
                }
                enemyAircrafts.add(enemyFactory.create());
            }
            // boss产生
            if (Main.settings.score != 0 && Main.settings.score % Main.settings.bossEnemyEmergeScore == 0) {
                BossEnemy bossEnemy = BossEnemy.getInstance();
                if (bossEnemy != null) {
                    enemyAircrafts.add(bossEnemy);
                    gameActivity.playMusic("bgm_boss");
                    bgm_boss = true;
                }
            }
            // 飞机射出子弹
            shootAction();
        }

        // 子弹移动
        bulletsMoveAction();

        // 飞机移动
        aircraftsMoveAction();

        // 道具移动
        propMoveAction();

        // 撞击检测
        crashCheckAction();

        // 后处理
        postProcessAction();

        // 游戏结束检查
        if (heroAircraft.getHp() <= 0) {
            // 游戏结束
            gameOverFlag = true;
            HeroAircraft.clearHeroAircraft();
            // 播放停止音效，停止背景音效
            gameActivity.playMusic("game_over");
            try {
                Thread.sleep(1200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.i("---pipeline---", "----Game Over----");
            gameActivity.gameOver();
        }
    }

    //***********************
    //      Action 各部分
    //***********************

    private boolean timeCountAndNewCycleJudge() {
        cycleTime += timeInterval;
        if (cycleTime >= cycleDuration && cycleTime - timeInterval < cycleTime) {
            // 跨越到新的周期
            cycleTime %= cycleDuration;
            return true;
        } else {
            return false;
        }
    }

    private void shootAction() {
        // 敌机射击
        for (int i = 0; i < enemyAircrafts.size(); i++) {
            if (enemyAircrafts.get(i) instanceof EliteEnemy) {
                enemyBullets.addAll(enemyAircrafts.get(i).shoot());
            }
        }

        // 英雄射击
        heroBullets.addAll(heroAircraft.shoot());
    }

    private void bulletsMoveAction() {
        for (BaseBullet bullet : heroBullets) {
            bullet.forward();
        }
        for (BaseBullet bullet : enemyBullets) {
            bullet.forward();
        }
    }

    private void aircraftsMoveAction() {
        for (AbstractAircraft enemyAircraft : enemyAircrafts) {
            enemyAircraft.forward();
        }
    }

    private void propMoveAction() {
        for (AbstractFlyingObject prop : props) {
            prop.forward();
        }
    }


    /**
     * 碰撞检测：
     * 1. 敌机攻击英雄
     * 2. 英雄攻击/撞击敌机
     * 3. 英雄获得补给
     */
    private void crashCheckAction() {
        // 敌机子弹攻击英雄
        for (BaseBullet bullet : enemyBullets) {
            if (bullet.notValid()) {
                continue;
            }
            if (heroAircraft.crash(bullet)) {
                heroAircraft.decreaseHp(bullet.getPower());
                bullet.vanish();
            }
        }

        // 英雄子弹攻击敌机
        for (BaseBullet bullet : heroBullets) {
            if (bullet.notValid()) {
                continue;
            }
            for (AbstractAircraft enemyAircraft : enemyAircrafts) {
                if (enemyAircraft.notValid()) {
                    // 已被其他子弹击毁的敌机，不再检测
                    // 避免多个子弹重复击毁同一敌机的判定
                    continue;
                }
                if (enemyAircraft.crash(bullet)) {
                    // 敌机撞击到英雄机子弹
                    // 播放音效
                    gameActivity.playMusic("bullet_hit");
                    // 敌机损失一定生命值
                    enemyAircraft.decreaseHp(bullet.getPower());
                    bullet.vanish();
                    if (enemyAircraft.notValid()) {
                        // 获得分数，精英机一定几率产生道具补给
                        Main.settings.score += 10;
                        AbstractProp prop = enemyAircraft.generateProp();
                        if (prop != null) {
                            props.add(prop);
                        }
                    }
                }
                // 英雄机 与 敌机 相撞，均损毁
                if (enemyAircraft.crash(heroAircraft) || heroAircraft.crash(enemyAircraft)) {
                    enemyAircraft.vanish();
                    heroAircraft.decreaseHp(Integer.MAX_VALUE);
                }
            }
        }

        //  我方获得道具，道具生效
        for (AbstractProp prop : props) {
            if (heroAircraft.crash(prop)) {
                gameActivity.playMusic(prop.getMusicType());
                if (prop instanceof BombProp) {
                    for (AbstractAircraft subscriber : enemyAircrafts) {
                        ((BombProp) prop).subscribe((Subscriber) subscriber);
                    }
                    for (BaseBullet subscriber : enemyBullets) {
                        ((BombProp) prop).subscribe((Subscriber) subscriber);
                    }
                }
                prop.vanish();
                prop.active(heroAircraft, enemyAircrafts, enemyBullets);
            }
        }
    }

    /**
     * 后处理：
     * 1. 删除无效的子弹
     * 2. 删除无效的敌机
     * 3. 删除无效的道具
     * 4. 重置弹道数
     * 5. 检查英雄机生存
     * <p>
     * 无效的原因可能是撞击或者飞出边界
     */
    private void postProcessAction() {
        if (!bgm_boss) {
            Log.i(TAG, "postProcessAction: ");
            gameActivity.playMusic("stop_bgm_boss");
        }
        enemyBullets.removeIf(AbstractFlyingObject::notValid);
        heroBullets.removeIf(AbstractFlyingObject::notValid);
        enemyAircrafts.removeIf(AbstractFlyingObject::notValid);
        props.removeIf(AbstractFlyingObject::notValid);
    }


    public void draw() {
        // 通过SurfaceHolder对象的lockCanvans()方法，我们可以获取当前的Canvas绘图对象
        canvas = mSurfaceHolder.lockCanvas();
        if (mSurfaceHolder == null || canvas == null) {
            return;
        }
        mPaint.setAntiAlias(true);
        // 绘制滚动背景
        canvas.drawBitmap(ImageManager.BACKGROUND_IMAGE, 0, this.backGroundTop - ImageManager.BACKGROUND_IMAGE_EASY.getHeight(), mPaint);
        canvas.drawBitmap(ImageManager.BACKGROUND_IMAGE, 0, this.backGroundTop, mPaint);
        backGroundTop += 3;
        if (backGroundTop == ImageManager.BACKGROUND_IMAGE.getHeight()) {
            this.backGroundTop = 0;
        }
        // 先绘制子弹，后绘制飞机
        // 这样子弹显示在飞机的下层
        paintImageWithPositionRevised(enemyBullets);
        paintImageWithPositionRevised(heroBullets);

        paintImageWithPositionRevised(props);

        paintImageWithPositionRevised(enemyAircrafts);

        canvas.drawBitmap(ImageManager.HERO_IMAGE, heroAircraft.getLocationX() - ImageManager.HERO_IMAGE.getWidth() / 2,
                heroAircraft.getLocationY() - ImageManager.HERO_IMAGE.getHeight() / 2, mPaint);

        // 绘制得分和生命值
        paintScoreAndLife();

        // 通过unlockCanvasAndPost(mCanvas)方法对画布内容进行提交
        mSurfaceHolder.unlockCanvasAndPost(canvas);
    }

    @Override
    public void run() {
        //设置一个循环来绘制，通过标志位来控制开启绘制还是停止
        while (!gameOverFlag) {
            synchronized (mSurfaceHolder) {
                action();
                draw();
            }
            try {
//                Thread.sleep(200);
                Thread.sleep(timeInterval);
            } catch (Exception e) {
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if ((Math.abs(heroAircraft.getLocationX() - x) < 50)
                        && (Math.abs(heroAircraft.getLocationY() - y) < 50))
                    heroAircraft.setLocation(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                if ((Math.abs(heroAircraft.getLocationX() - x) < 100)
                        && (Math.abs(heroAircraft.getLocationY() - y) < 100))
                    heroAircraft.setLocation(x, y);
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        screenWidth = width;
        screenHeight = height;
        Main.WINDOW_WIDTH = width;
        Main.WINDOW_HEIGHT = height;
        heroAircraft.setLocation(Main.WINDOW_WIDTH / 2,
                Main.WINDOW_HEIGHT - ImageManager.HERO_IMAGE.getHeight());
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        gameOverFlag = true;
    }

    public void loadImage() {
        ImageManager.BACKGROUND_IMAGE_EASY = BitmapFactory.decodeResource(getResources(), R.drawable.bg);
        ImageManager.BACKGROUND_IMAGE_NORMAL = BitmapFactory.decodeResource(getResources(), R.drawable.bg3);
        ImageManager.BACKGROUND_IMAGE_HARD = BitmapFactory.decodeResource(getResources(), R.drawable.bg5);
        ImageManager.HERO_IMAGE = BitmapFactory.decodeResource(getResources(), R.drawable.hero);
        ImageManager.ELITE_ENEMY_IMAGE = BitmapFactory.decodeResource(getResources(), R.drawable.elite);
        ImageManager.BOSS_ENEMY_IMAGE = BitmapFactory.decodeResource(getResources(), R.drawable.boss);
        ImageManager.MOB_ENEMY_IMAGE = BitmapFactory.decodeResource(getResources(), R.drawable.mob);
        ImageManager.HERO_BULLET_IMAGE = BitmapFactory.decodeResource(getResources(), R.drawable.bullet_hero);
        ImageManager.ENEMY_BULLET_IMAGE = BitmapFactory.decodeResource(getResources(), R.drawable.bullet_enemy);
        ImageManager.BLOOD_PROP_IMAGE = BitmapFactory.decodeResource(getResources(), R.drawable.prop_blood);
        ImageManager.BOMB_PROP_IMAGE = BitmapFactory.decodeResource(getResources(), R.drawable.prop_bomb);
        ImageManager.BULLET_PROP_IMAGE = BitmapFactory.decodeResource(getResources(), R.drawable.prop_bullet);

        ImageManager.putMap();

        switch (Settings.backGroundIndex / 2) {
            case 0:
                ImageManager.BACKGROUND_IMAGE = ImageManager.BACKGROUND_IMAGE_EASY;
                break;
            case 1:
                ImageManager.BACKGROUND_IMAGE = ImageManager.BACKGROUND_IMAGE_NORMAL;
                break;
            case 2:
                ImageManager.BACKGROUND_IMAGE = ImageManager.BACKGROUND_IMAGE_HARD;
                break;
            default:
        }
    }

    private void paintImageWithPositionRevised(List<? extends AbstractFlyingObject> objects) {
        if (objects.size() == 0) {
            return;
        }

        for (AbstractFlyingObject object : objects) {
            Bitmap image = object.getImage();
            assert image != null : objects.getClass().getName() + " has no image! ";
            canvas.drawBitmap(image, object.getLocationX() - image.getWidth() / 2,
                    object.getLocationY() - image.getHeight() / 2, mPaint);
        }
    }

    private void paintScoreAndLife() {
        int x = (int) (screenWidth * 0.0185);
        int y = (int) (screenHeight * 0.0228);
        int textSize = (int) (screenHeight * 0.0228);
        mPaint.setColor(Color.RED);
        mPaint.setTextSize(textSize);
        mPaint.setTypeface(Typeface.DEFAULT_BOLD);
        canvas.drawText("SCORE:" + Main.settings.score, x, y, mPaint);
        y *= 2;
        canvas.drawText("LIFE:" + this.heroAircraft.getHp(), x, y, mPaint);
    }
}

