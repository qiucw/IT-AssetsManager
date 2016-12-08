/**
 * Chenwei Qiu
 * 11/8/2016
 * GameActivity.java
 */
package qiucw.tacoma.uw.edu.tcss445team1;


import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

/**
 *  This class represents the game page
 */
public class GameActivity extends AppCompatActivity {
    private String current_user;
    private ImageView stickman, ball;
    private int score;
    private AnimationDrawable stickmanAnimation;
    private boolean touchable = true;
    private boolean final_touchable = true;
    private boolean count = true;
    private Rect rc1, rc2;
    private ValueAnimator background;
    private TextView scoreText, endHint;
    private int duration = 4000;
    /**
     * this method create the every component in this activity in the beginning as well as listeners
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Bundle extras = getIntent().getExtras();
        current_user = extras.getString("username");

        endHint = (TextView) findViewById(R.id.finished);
        stickman = (ImageView) findViewById(R.id.stickman);
        ball = (ImageView) findViewById(R.id.ball);
        scoreText = (TextView) findViewById(R.id.score);
        rc1 = new Rect();
        rc2 = new Rect();
        stickman.setBackgroundResource(R.drawable.walk);
        stickmanAnimation = (AnimationDrawable) stickman.getBackground();
        stickmanAnimation.start();

        ball.animate().translationX(-2000).setDuration(duration);
        ball.animate().setInterpolator(new LinearInterpolator());
        ball.animate().setUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                if(ball.getX() < 0){
                    count = !count;
                    ball.setX(2000);
                    ball.animate().translationX(-2000).setDuration(4000);
                }

                if (count && ball.getX() + ball.getWidth() < stickman.getX()){
                    count = !count;
                    score++;
                    scoreText.setText("Score: " + score);
                }

                ball.getHitRect(rc1);
                stickman.getHitRect(rc2);
                if (Rect.intersects(rc1, rc2)) {
                    final_touchable = false;
                    stickmanAnimation.stop();
                    background.cancel();
                    ball.animate().cancel();
                    ball.setVisibility(View.GONE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            stickman.animate().cancel();
                            stickman.animate().translationY(-150).setDuration(400);
                        }
                    }, 500);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            stickman.animate().translationY(800).setDuration(800);
                            endHint.setText("G        ");
                        }
                    }, 1050);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            endHint.setText("GA       ");
                        }
                    }, 1250);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            endHint.setText("GAM      ");
                        }
                    }, 1450);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            endHint.setText("GAME     ");
                        }
                    }, 1650);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            endHint.setText("GAME O   ");
                        }
                    }, 1850);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            endHint.setText("GAME OV  ");
                        }
                    }, 2050);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            endHint.setText("GAME OVE ");
                        }
                    }, 2250);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            endHint.setText("GAME OVER");
                        }
                    }, 2450);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent i = new Intent(GameActivity.this, GameoverActivity.class);
                            i.putExtra("username", current_user);
                            i.putExtra("score", score);
                            startActivity(i);
                            finish();
                        }
                    }, 3450);
                }
            }
        });


        final ImageView backgroundOne = (ImageView) findViewById(R.id.background_one);
        final ImageView backgroundTwo = (ImageView) findViewById(R.id.background_two);
        background = ValueAnimator.ofFloat(1.0f, 0.0f);
        background.setRepeatCount(ValueAnimator.INFINITE);
        background.setInterpolator(new LinearInterpolator());
        background.setDuration(duration * 833 /1000);
        background.start();

        background.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
            final float progress = (float) animation.getAnimatedValue();
            final float width = backgroundOne.getWidth();
            final float translationX = width * progress;
            backgroundOne.setTranslationX(translationX);
            backgroundTwo.setTranslationX(translationX - width);
            }
        });
    }

    /**
     * this is the touch listener
     * @param event
     * @return true
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(touchable && final_touchable){
            final MediaPlayer mp = MediaPlayer.create(this, R.raw.jump);
            mp.start();
            touchable = false;
            stickmanAnimation.stop();
            stickman.animate().setInterpolator(new DecelerateInterpolator());
            stickman.animate().translationY(-400).setDuration(500);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    stickman.animate().setInterpolator(new AccelerateInterpolator());
                    stickman.animate().translationY(0).setDuration(500);
                }
            }, 450);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    stickmanAnimation.start();
                }
            }, 950);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    touchable = true;
                    mp.stop();
                }
            }, 1000);
        }
        return true;
    }
}
