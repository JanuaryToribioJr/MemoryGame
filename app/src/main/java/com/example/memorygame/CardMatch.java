package com.example.memorygame;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.memorygame.R;

import java.util.Collections;
import java.util.ArrayList;

public class CardMatch extends BaseAdapter {
    private final int[] images;
    private Context context;
    private ArrayList<Integer> imageList;
    private ImageView[] views;
    private int firstCard = -1, secondCard = -1;
    private boolean isBusy = false;
    private int matchedPairs = 0;
    private int totalPairs;

    
    public CardMatch(Context context, int[] images) {
        this.context = context;
        this.images = images;
        this.totalPairs = images.length / 2;
        imageList = new ArrayList<>();
        for (int img : images) imageList.add(img);
        Collections.shuffle(imageList);
        views = new ImageView[imageList.size()];

    }

    @Override
    public int getCount() {
        return imageList.size();
    }

    @Override
    public Object getItem(int i) {
        return imageList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup parent) {
        if (views[i] == null) {
            views[i] = new ImageView(context);
            views[i].setLayoutParams(new ViewGroup.LayoutParams(200, 200));
            views[i].setScaleType(ImageView.ScaleType.CENTER_CROP);
            views[i].setImageResource(R.drawable.card_bg);
        }

        views[i].setOnClickListener(v -> {
            if (isBusy || views[i].getTag() == "matched") return;

            views[i].setImageResource(imageList.get(i));

            if (firstCard == -1) {
                firstCard = i;
            } else if (secondCard == -1 && i != firstCard) {
                secondCard = i;
                isBusy = true;

                if (context instanceof MainActivity) {
                    ((MainActivity) context).incrementMoveCount();
                }

                new Handler().postDelayed(() -> {
                    if (imageList.get(firstCard).equals(imageList.get(secondCard))) {
                        views[firstCard].setTag("matched");
                        views[secondCard].setTag("matched");
                        matchedPairs++;

                        if (matchedPairs == totalPairs) {
                            if (context instanceof MainActivity) {
                                ((MainActivity) context).showCongrats();
                                Toast.makeText(context, "All cards matched!", Toast.LENGTH_SHORT).show();
                            }
                        }

                    } else {
                        views[firstCard].setImageResource(R.drawable.card_bg);
                        views[secondCard].setImageResource(R.drawable.card_bg);
                    }
                    firstCard = secondCard = -1;
                    isBusy = false;
                }, 1000);
            }
        });

        return views[i];
    }

}
