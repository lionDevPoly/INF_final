package lion.favouritespots;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

/**
 * Created by opris_000 on 3/31/2016.
 */
public class ListViewItem implements ImageFileLoaderTask.OnImageLoaderCompletedListener {

    private Bitmap thumbnail;

    private boolean selected = false;
    private boolean selectable = false;

    private Animation animationBegin;
    private Animation animationEnd;

    private ListViewItemHolder curHolder;
    private Context curContext;
    private FancyPlace curFancyPlace;

    public ListViewItem(Context context, ListViewItemHolder holder) {

        curContext = context;
        curHolder = holder;


        animationBegin = AnimationUtils.loadAnimation(context, R.anim.to_middle);
        animationEnd = AnimationUtils.loadAnimation(context, R.anim.from_middle);

        thumbnail = ((BitmapDrawable) curHolder.thumbnailView.getDrawable()).getBitmap();
    }

    public void setHolder(ListViewItemHolder holder) {
        curHolder = holder;
        updateContent();
    }

    public void setFancyPlace(FancyPlace fancyPlace)
    {
        curFancyPlace = fancyPlace;

        if (fancyPlace.getImage().exists()) {
            ImageFileLoaderTask backgroundTask = new ImageFileLoaderTask(this);
            backgroundTask.execute(fancyPlace.getImage());
        }

        setSelected(isSelected());
    }

    protected void updateContent() {
        curHolder.titleTextView.setText(curFancyPlace.getTitle());

        if (selected) {
            curHolder.backgroundLayoutView.setBackgroundColor(curContext.getResources().getColor(R.color.ColorBackgroundAccent));
            curHolder.thumbnailView.setImageResource(R.drawable.ic_done_white_48dp);
        } else {
            curHolder.backgroundLayoutView.setBackgroundColor(curContext.getResources().getColor(R.color.ColorBackground));
            curHolder.thumbnailView.setImageBitmap(thumbnail);
        }
    }

    public boolean isSelectable()
    {
        return selectable;
    }

    public void setSelectable(boolean _selectable)
    {
        selectable = _selectable;

        if (!selectable)
            setSelected(false);
    }

    public void toggleAndAnimateSelected() {
        startTogglingWithAnimation();
    }

    public void setAndAnimateSelected(boolean _selected) {
        if (selected != _selected)
            startTogglingWithAnimation();
    }

    public boolean isSelected()
    {
        return selected;
    }

    protected void setSelected(boolean _selected)
    {
        selected = _selected;


        updateContent();
    }

    private void startTogglingWithAnimation() {
        curHolder.thumbnailView.clearAnimation();
        curHolder.thumbnailView.setAnimation(animationBegin);
        curHolder.thumbnailView.startAnimation(animationBegin);

        Animation.AnimationListener animListener;
        animListener = new FlipAnimationListener();

        animationBegin.setAnimationListener(animListener);
        animationEnd.setAnimationListener(animListener);
    }

    @Override
    public void onImageLoaderCompleted(Bitmap bitmap) {
        thumbnail = bitmap;
        updateContent();
    }


    private class FlipAnimationListener implements Animation.AnimationListener
    {
        public FlipAnimationListener(){}
        @Override
        public void onAnimationStart(Animation animation) {
        }


        @Override
        public void onAnimationRepeat(Animation arg0) {
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            if (animation == animationBegin) {
                setSelected(!isSelected());

                curHolder.thumbnailView.clearAnimation();
                curHolder.thumbnailView.setAnimation(animationEnd);
                curHolder.thumbnailView.startAnimation(animationEnd);
            } else {
                curHolder.thumbnailView.clearAnimation();
            }
        }
    }
}
