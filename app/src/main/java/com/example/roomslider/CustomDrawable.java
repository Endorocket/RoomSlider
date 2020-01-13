package com.example.roomslider;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.drawable.Drawable;

public class CustomDrawable extends Drawable {
  private final Paint circlePaint;
  private final Bitmap bitmap;
  private final int imageIndex;
  private final boolean isHint;
  private final Point coords;

  public CustomDrawable(Bitmap bitmap, int imageIndex, boolean isCircle, boolean isHint, Point coords) {
    this.bitmap = bitmap;
    this.imageIndex = imageIndex;
    this.isHint = isHint;
    this.coords = coords;

    circlePaint = new Paint();
    int color = 30;

    if (isCircle)
      circlePaint.setARGB(140, color, color, color);
    else
      circlePaint.setARGB(0, color, color, color);

    // empty center
    circlePaint.setStrokeWidth(8);
    circlePaint.setStyle(Paint.Style.STROKE);
  }

  @Override
  public void draw(Canvas canvas) {
    int width = getBounds().width();
    int height = getBounds().height();
    float radius = Math.min(width, height) / 7;

    // draw previous image first
    canvas.drawBitmap(bitmap, 0, 0, null);

    // add circle to image
    Point[] points = calculatePoints();

    for (Point point : points) {
      canvas.drawCircle(point.x, point.y, radius, circlePaint);
    }

    if (isHint)
      drawHint(canvas, (int) radius);

//    printPointsInfo(canvas, radius, points);
  }

  private int[] glassIndices = new int[]{40, 50, 65, 75, 100, 120, 301};
  private int[] glassXCoords = new int[]{550, 400, 400, 320, 300, 200, -1000};
  private int[] glassYCoords = new int[]{380, 370, 370, 370, 370, 370, -1000};

  private int[] bookIndices = new int[]{40, 50, 60, 301};
  private int[] bookXCoords = new int[]{540, 450, 450, -1000};
  private int[] bookYCoords = new int[]{180, 70, 50, -1000};

  private int[] boxIndices = new int[]{160, 170, 180, 190, 210, 220, 240, 250, 301};
  private int[] boxXCoords = new int[]{-1000, 900, 800, 700, 650, 650, 560, 570, 500};
  private int[] boxYCoords = new int[]{-1000, 340, 350, 350, 350, 350, 350, 340, 350};

  private void drawHint(Canvas canvas, int radius) {
    Paint textPaint = new Paint();
    textPaint.setColor(Color.BLACK);
    textPaint.setTextSize(32);

    Paint rectanglePaint = new Paint();
    rectanglePaint.setColor(Color.argb(200, 255, 255, 255));

    // glass
    for (int i = 0; i < glassIndices.length; i++) {
      if (imageIndex < glassIndices[i]) {
        int glassX = glassXCoords[i];
        int glassY = glassYCoords[i];
        if (coords.x > glassX - radius / 3 && coords.x < glassX + radius / 3 && coords.y > glassY - radius && coords.y < glassY + radius) {

          int offsetY = 200;
          canvas.drawRoundRect(glassX - 100, glassY - 50 - offsetY, glassX + 250, glassY + 50 - offsetY, 10, 10, rectanglePaint);
          canvas.drawText("Piękny kieliszek", glassX - 50, glassY - offsetY, textPaint);
        }
        break;
      }
    }

    // book
    for (int i = 0; i < bookIndices.length; i++) {
      if (imageIndex < bookIndices[i]) {
        int bookX = bookXCoords[i];
        int bookY = bookYCoords[i];
        if (coords.x > bookX - radius / 3 && coords.x < bookX + radius / 3 && coords.y > bookY - radius && coords.y < bookY + radius) {

          int offsetY = -100;
          canvas.drawRoundRect(bookX - 100, bookY - 50 - offsetY, bookX + 350, bookY + 50 - offsetY, 10, 10, rectanglePaint);
          canvas.drawText("Wiedźmin i Pani Jeziora", bookX - 50, bookY - offsetY, textPaint);
        }
        break;
      }
    }

    // box
    for (int i = 0; i < boxIndices.length; i++) {
      if (imageIndex < boxIndices[i]) {
        int boxX = boxXCoords[i];
        int boxY = boxYCoords[i];
        if (coords.x > boxX - radius / 3 && coords.x < boxX + radius / 3 && coords.y > boxY - radius && coords.y < boxY + radius) {

          int offsetY = 200;
          canvas.drawRoundRect(boxX - 200, boxY - 50 - offsetY, boxX + 250, boxY + 50 - offsetY, 10, 10, rectanglePaint);
          canvas.drawText("Pudełko pełne narzędzi", boxX - 150, boxY - offsetY, textPaint);
        }
        break;
      }
    }
  }


  private Point[] calculatePoints() {
    Point[] points = new Point[3];

    Point pointGlass = new Point();

    if (imageIndex < glassIndices[0])
      pointGlass.set(glassXCoords[0], glassYCoords[0]);
    else if (imageIndex < glassIndices[1])
      pointGlass.set(glassXCoords[1], glassYCoords[1]);
    else if (imageIndex < glassIndices[2])
      pointGlass.set(glassXCoords[2], glassYCoords[2]);
    else if (imageIndex < glassIndices[3])
      pointGlass.set(glassXCoords[3], glassYCoords[3]);
    else if (imageIndex < glassIndices[4])
      pointGlass.set(glassXCoords[4], glassYCoords[4]);
    else if (imageIndex < glassIndices[5])
      pointGlass.set(glassXCoords[5], glassYCoords[5]);
    else
      pointGlass.set(glassXCoords[6], glassYCoords[6]);

    points[0] = pointGlass;

    Point pointBook = new Point();

    if (imageIndex < bookIndices[0])
      pointBook.set(bookXCoords[0], bookYCoords[0]);
    else if (imageIndex < bookIndices[1])
      pointBook.set(bookXCoords[1], bookYCoords[1]);
    else if (imageIndex < bookIndices[2])
      pointBook.set(bookXCoords[2], bookYCoords[2]);
    else
      pointBook.set(bookXCoords[3], bookYCoords[3]);

    points[1] = pointBook;

    Point pointBox = new Point();

    if (imageIndex < boxIndices[0])
      pointBox.set(boxXCoords[0], boxYCoords[0]);
    else if (imageIndex < boxIndices[1])
      pointBox.set(boxXCoords[1], boxYCoords[1]);
    else if (imageIndex < boxIndices[2])
      pointBox.set(boxXCoords[2], boxYCoords[2]);
    else if (imageIndex < boxIndices[3])
      pointBox.set(boxXCoords[3], boxYCoords[3]);
    else if (imageIndex < boxIndices[4])
      pointBox.set(boxXCoords[4], boxYCoords[4]);
    else if (imageIndex < boxIndices[5])
      pointBox.set(boxXCoords[5], boxYCoords[5]);
    else if (imageIndex < boxIndices[6])
      pointBox.set(boxXCoords[6], boxYCoords[6]);
    else if (imageIndex < boxIndices[7])
      pointBox.set(boxXCoords[7], boxYCoords[7]);
    else
      pointBox.set(boxXCoords[8], boxYCoords[8]);

    points[2] = pointBox;

    return points;
  }

  private void printPointsInfo(Canvas canvas, float radius, Point[] points) {
    System.out.println("\n");

    for (int i = 0; i < 3; i++) {
      if (i == 0)
        System.out.println("Kieliszek: " + points[i]);
      else if (i == 1)
        System.out.println("Ksiazka: " + points[i]);
      else
        System.out.println("Pudelko: " + points[i]);

      System.out.println("Index obrazka: " + imageIndex);
    }
  }

  @Override
  public void setAlpha(int alpha) {
    // This method is required
  }

  @Override
  public void setColorFilter(ColorFilter colorFilter) {
    // This method is required
  }

  @Override
  public int getOpacity() {
    // Must be PixelFormat.UNKNOWN, TRANSLUCENT, TRANSPARENT, or OPAQUE
    return PixelFormat.OPAQUE;
  }
}