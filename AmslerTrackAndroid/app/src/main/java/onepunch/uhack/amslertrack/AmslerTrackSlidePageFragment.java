package onepunch.uhack.amslertrack;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class AmslerTrackSlidePageFragment extends Fragment {
    private int mIndex;

    public void setIndex(int nIndex) {
        mIndex = nIndex;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_amsler_track_page, container, false);

        TextView textView = (TextView)rootView.getChildAt(0);
        textView.setText(textIdFromIndex(mIndex));

        ImageView viewImage = (ImageView)rootView.getChildAt(1);
        viewImage.setImageResource(imageIdFromIndex(mIndex));

        Button button = (Button)rootView.getChildAt(2);
        button.setText(btnTextIdFromIndex(mIndex));
        final AmslerTrackSlidePagerActivity slideAct = (AmslerTrackSlidePagerActivity)getActivity();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mIndex == 0) {
                    slideAct.getPager().setCurrentItem(slideAct.getPager().getCurrentItem() + 1);
                }
                else {
                    slideAct.finish();
                }

            }
        });

        return rootView;
    }

    private int textIdFromIndex(int nIndex) {
        switch (nIndex) {
            case 0:
                return R.string.text_tip_1;
            case 1:
                return R.string.text_tip_2;
            default:
                return R.string.app_name;
        }
    }

    private int btnTextIdFromIndex(int nIndex) {
        switch (nIndex) {
            case 0:
                return R.string.button_text_next;
            case 1:
                return R.string.button_text_ok;
            default:
                return R.string.button_text_ok;
        }
    }

    private int imageIdFromIndex(int nIndex) {
        switch (nIndex) {
            case 0:
                return R.drawable.slide_page_1;
            case 1:
                return R.drawable.slide_page_2;
                default:
                    return R.drawable.slide_page_1;
        }

    }
}
