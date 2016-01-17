package tempcontroller.challenge.com.challenge75f.listeners;

/**
 * Created by Hemant on 9/28/15.
 */

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Detects left and right swipes across a view.
 */
public class OnSwipeTouchListener implements View.OnTouchListener {

    private static final int SWIPE_DISTANCE_THRESHOLD = 100;

    private final GestureDetector gestureDetector;
    private SwipeTouchListener mInterface;

    public OnSwipeTouchListener(Context context) {
        mInterface = (SwipeTouchListener)context;
        gestureDetector = new GestureDetector(context, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                if(distanceX <= 0) {
                    mInterface.onSwipeRight();
                }else {
                    mInterface.onSwipeLeft();
                }
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {

            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                return true;
            }
        });
    }

    /**
     * Interface to detect the swipe direction.
     */
    public interface SwipeTouchListener {
        void onSwipeLeft();
        void onSwipeRight();
    }

    private void rotate(View v, MotionEvent event) {

        switch (event.getAction() & MotionEvent.ACTION_MASK) {

            case MotionEvent.ACTION_MOVE:
                System.out.println("Action move touch");
                gestureDetector.onTouchEvent(event);

                break;
        }
    }


    public boolean onTouch(View v, MotionEvent event) {
        int eventId = event.getAction();

        switch (eventId) {
            case MotionEvent.ACTION_MOVE:
                rotate(v, event);
                break;
        }
        return true;
    }

    /*private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

        private static final int SWIPE_DISTANCE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float distanceX = e2.getX() - e1.getX();
            float distanceY = e2.getY() - e1.getY();
            if (Math.abs(distanceX) > Math.abs(distanceY) && Math.abs(distanceX) > SWIPE_DISTANCE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                if (distanceX > 0)
                    onSwipeRight();
                else
                    onSwipeLeft();
                return true;
            }
            return false;
        }
    }*/
}
