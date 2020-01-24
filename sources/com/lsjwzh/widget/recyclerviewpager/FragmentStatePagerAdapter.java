package com.lsjwzh.widget.recyclerviewpager;

import android.annotation.TargetApi;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.Fragment.SavedState;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnAttachStateChangeListener;
import android.view.ViewGroup;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@TargetApi(12)
public abstract class FragmentStatePagerAdapter extends Adapter<FragmentViewHolder> {
    private static final boolean DEBUG = false;
    private static final String TAG = "FragmentStatePagerAdapter";
    private IContainerIdGenerator mContainerIdGenerator = new IContainerIdGenerator() {
        private Random mRandom = new Random();

        public int genId(Set<Integer> set) {
            return Math.abs(this.mRandom.nextInt());
        }
    };
    /* access modifiers changed from: private */
    public FragmentTransaction mCurTransaction = null;
    /* access modifiers changed from: private */
    public final FragmentManager mFragmentManager;
    private Set<Integer> mIds = new HashSet();
    /* access modifiers changed from: private */
    public SparseArray<SavedState> mStates = new SparseArray<>();

    public class FragmentViewHolder extends ViewHolder implements OnAttachStateChangeListener {
        public FragmentViewHolder(View itemView) {
            super(itemView);
            itemView.addOnAttachStateChangeListener(this);
        }

        public void onViewAttachedToWindow(View v) {
            if (FragmentStatePagerAdapter.this.mCurTransaction == null) {
                FragmentStatePagerAdapter.this.mCurTransaction = FragmentStatePagerAdapter.this.mFragmentManager.beginTransaction();
            }
            int tagId = FragmentStatePagerAdapter.this.genTagId(getLayoutPosition());
            Fragment fragmentInAdapter = FragmentStatePagerAdapter.this.getItem(getLayoutPosition(), (SavedState) FragmentStatePagerAdapter.this.mStates.get(tagId));
            if (fragmentInAdapter != null) {
                FragmentStatePagerAdapter.this.mCurTransaction.replace(this.itemView.getId(), fragmentInAdapter, tagId + "");
                FragmentStatePagerAdapter.this.mCurTransaction.commitAllowingStateLoss();
                FragmentStatePagerAdapter.this.mCurTransaction = null;
                FragmentStatePagerAdapter.this.mFragmentManager.executePendingTransactions();
            }
        }

        public void onViewDetachedFromWindow(View v) {
            int tagId = FragmentStatePagerAdapter.this.genTagId(getLayoutPosition());
            Fragment frag = FragmentStatePagerAdapter.this.mFragmentManager.findFragmentByTag(tagId + "");
            if (frag != null) {
                if (FragmentStatePagerAdapter.this.mCurTransaction == null) {
                    FragmentStatePagerAdapter.this.mCurTransaction = FragmentStatePagerAdapter.this.mFragmentManager.beginTransaction();
                }
                FragmentStatePagerAdapter.this.mStates.put(tagId, FragmentStatePagerAdapter.this.mFragmentManager.saveFragmentInstanceState(frag));
                FragmentStatePagerAdapter.this.mCurTransaction.remove(frag);
                FragmentStatePagerAdapter.this.mCurTransaction.commitAllowingStateLoss();
                FragmentStatePagerAdapter.this.mCurTransaction = null;
                FragmentStatePagerAdapter.this.mFragmentManager.executePendingTransactions();
                FragmentStatePagerAdapter.this.onDestroyItem(getLayoutPosition(), frag);
            }
        }
    }

    public interface IContainerIdGenerator {
        int genId(Set<Integer> set);
    }

    public abstract Fragment getItem(int i, SavedState savedState);

    public abstract void onDestroyItem(int i, Fragment fragment);

    public FragmentStatePagerAdapter(FragmentManager fm) {
        this.mFragmentManager = fm;
    }

    public void setContainerIdGenerator(@NonNull IContainerIdGenerator idGenerator) {
        this.mContainerIdGenerator = idGenerator;
    }

    public void onViewRecycled(FragmentViewHolder holder) {
        if (this.mCurTransaction == null) {
            this.mCurTransaction = this.mFragmentManager.beginTransaction();
        }
        int tagId = genTagId(holder.getAdapterPosition());
        Fragment f = this.mFragmentManager.findFragmentByTag(tagId + "");
        if (f != null) {
            this.mStates.put(tagId, this.mFragmentManager.saveFragmentInstanceState(f));
            this.mCurTransaction.remove(f);
            this.mCurTransaction.commitAllowingStateLoss();
            this.mCurTransaction = null;
            this.mFragmentManager.executePendingTransactions();
        }
        if (holder.itemView instanceof ViewGroup) {
            ((ViewGroup) holder.itemView).removeAllViews();
        }
        super.onViewRecycled(holder);
    }

    public final FragmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rvp_fragment_container, parent, false);
        int id = this.mContainerIdGenerator.genId(this.mIds);
        if (parent.getContext() instanceof Activity) {
            while (((Activity) parent.getContext()).getWindow().getDecorView().findViewById(id) != null) {
                id = this.mContainerIdGenerator.genId(this.mIds);
            }
        }
        view.findViewById(R.id.rvp_fragment_container).setId(id);
        this.mIds.add(Integer.valueOf(id));
        return new FragmentViewHolder(view);
    }

    public final void onBindViewHolder(FragmentViewHolder holder, int position) {
    }

    /* access modifiers changed from: protected */
    public int genTagId(int position) {
        long itemId = getItemId(position);
        if (itemId == -1) {
            return position + 1;
        }
        return (int) itemId;
    }
}
