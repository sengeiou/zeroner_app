package android.support.v7.widget;

import android.support.v7.widget.RecyclerView.ViewHolder;

public abstract class ViewHolderDelegate {
    private ViewHolderDelegate() {
        throw new UnsupportedOperationException("no instances");
    }

    public static void setPosition(ViewHolder viewHolder, int position) {
        viewHolder.mPosition = position;
    }
}
