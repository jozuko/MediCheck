package com.studiojozu.medicheck.resource.uicomponent.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.studiojozu.medicheck.R;
import com.studiojozu.medicheck.application.TimetableSelectService;
import com.studiojozu.medicheck.domain.model.medicine.Medicine;
import com.studiojozu.medicheck.domain.model.setting.Timetable;
import com.studiojozu.medicheck.resource.uicomponent.listview.MultiSelectArrayAdapter;
import com.studiojozu.medicheck.resource.uicomponent.listview.MultiSelectItem;
import com.studiojozu.medicheck.resource.uicomponent.listview.TimetableListView;

import java.util.ArrayList;
import java.util.List;

/**
 * タイムテーブル選択ダイアログ
 */
public class TimetableSelectorDialogView extends ADialogView<TimetableListView> {

    private static final FrameLayout.LayoutParams LAYOUT_PARAMS = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    @NonNull
    private final CheckedTextView mOneShotCheckedTextView;
    @NonNull
    private final ListView mTimetableListView;
    @Nullable
    private TimetableSelectService mTimetableService = null;
    @Nullable
    private TimetableSelectorDialogView.OnSelectedListener mClientOnSelectedListener = null;

    public TimetableSelectorDialogView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, new TimetableListView(context));
        initTimetableService(context);

        mOneShotCheckedTextView = mDialogTargetView.findViewById(R.id.one_shot_check_text_view);
        mTimetableListView = mDialogTargetView.findViewById(R.id.timetable_list_view);

        initTargetView(LAYOUT_PARAMS, true, true);
    }

    private void initTimetableService(@NonNull Context context) {
        mTimetableService = new TimetableSelectService(context);
    }

    public void showDialog(@NonNull Medicine medicine) {
        if (mTimetableService == null) return;

        mOneShotCheckedTextView.setChecked(medicine.isOneShowMedicine());

        final MultiSelectArrayAdapter<Timetable> multiSelectArrayAdapter = mTimetableService.getTimetableSelectAdapter(medicine.getTimetableList());
        mTimetableListView.setAdapter(multiSelectArrayAdapter);

        setOnOkButtonClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mClientOnSelectedListener == null) {
                    closeDialog();
                    return;
                }

                List<MultiSelectItem<Timetable>> selectedItems = multiSelectArrayAdapter.getCheckedItems();
                List<Timetable> selectedTimetableList = new ArrayList<>();
                for (MultiSelectItem<Timetable> selectedItem : selectedItems) {
                    selectedTimetableList.add(selectedItem.getTag());
                }

                mClientOnSelectedListener.onSelected(mOneShotCheckedTextView.isChecked(), selectedTimetableList);
                closeDialog();
            }
        });

        super.showDialog();
    }

    public void setOnSelectedListener(TimetableSelectorDialogView.OnSelectedListener listener) {
        mClientOnSelectedListener = listener;
    }

    public interface OnSelectedListener {
        void onSelected(boolean isOneShot, @NonNull List<Timetable> timetableList);
    }
}
