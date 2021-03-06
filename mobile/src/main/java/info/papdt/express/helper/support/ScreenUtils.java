package info.papdt.express.helper.support;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;

public class ScreenUtils {

	public static int getStatusBarHeight(Context context) {
		int result = 0;
		int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			result = context.getResources().getDimensionPixelSize(resourceId);
		}
		return result;
	}

	private static int getMiddleValue(int prev, int next, float factor) {
		return Math.round(prev + (next - prev) * factor);
	}

	public static int getMiddleColor(int prevColor, int curColor, float factor) {
		if (prevColor == curColor) {
			return curColor;
		};

		if (factor == 0f) {
			return prevColor;
		} else if(factor == 1f) {
			return curColor;
		}

		int a = getMiddleValue(Color.alpha(prevColor), Color.alpha(curColor), factor);
		int r = getMiddleValue(Color.red(prevColor), Color.red(curColor), factor);
		int g = getMiddleValue(Color.green(prevColor), Color.green(curColor), factor);
		int b = getMiddleValue(Color.blue(prevColor), Color.blue(curColor), factor);

		return Color.argb(a, r, g, b);
	}

	public static int getColor(int baseColor, float alphaPercent) {
		int alpha = Math.round(Color.alpha(baseColor) * alphaPercent);

		return (baseColor & 0x00FFFFFF) | (alpha << 24);
	}

	public static float dpToPx(Context context, float dp) {
		return TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP,
				dp,
				context.getResources().getDisplayMetrics()
		) + 0.5f;
	}

	public static Bitmap drawableToBitmap(Drawable drawable) {
		Bitmap bitmap = null;

		if (drawable instanceof BitmapDrawable) {
			BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
			if(bitmapDrawable.getBitmap() != null) {
				return bitmapDrawable.getBitmap();
			}
		}

		if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
			bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
		} else {
			bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
		}

		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
		drawable.draw(canvas);
		return bitmap;
	}
	
}
