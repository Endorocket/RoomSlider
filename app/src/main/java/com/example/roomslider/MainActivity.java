package com.example.roomslider;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

  private List<Integer> drawableIds = new ArrayList<>(300);
  private int imageIndex = 0;

  @SuppressLint("ClickableViewAccessibility")
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

    getImages();

    final ImageView imageView = findViewById(R.id.imageView);

    drawCircle(imageView, true);


    imageView.setOnTouchListener(new OnSwipeTouchListener(MainActivity.this) {
      public void onSwipeRight() {
        if (imageIndex > 0) {
          imageIndex--;
          drawCircle(imageView, false);
        }
      }

      public void onSwipeLeft() {
        if (imageIndex < 300) {
          imageIndex++;
          drawCircle(imageView, false);
        }
      }

      @Override
      public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
          drawCircle(imageView, false, true, new Point((int) event.getX(), (int) event.getY()));
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
          drawCircle(imageView, true);
        }
        return super.onTouch(v, event);
      }
    });
  }

  private void drawCircle(ImageView imageView, boolean isCircle) {
    drawCircle(imageView, isCircle, false, new Point(-1000, -1000));
  }

  private void drawCircle(ImageView imageView, boolean isCircle, boolean isHint, Point coords) {
    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), drawableIds.get(imageIndex));

    CustomDrawable customDrawable = new CustomDrawable(bitmap, imageIndex, isCircle, isHint, coords);
    imageView.setImageDrawable(customDrawable);
  }

  private void getImages() {
    for (int i = 0; i <= 300; i++) {
      String index;

      if (i < 10)
        index = "00" + i;
      else if (i < 100)
        index = "0" + i;
      else
        index = String.valueOf(i);

      String name = "room_project0" + index;
      int drawableId = getResources().getIdentifier(name, "drawable", getPackageName());
      drawableIds.add(drawableId);
    }
  }
}