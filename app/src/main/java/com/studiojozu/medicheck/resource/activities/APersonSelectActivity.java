package com.studiojozu.medicheck.resource.activities;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.studiojozu.medicheck.R;
import com.studiojozu.medicheck.application.PersonSelectService;
import com.studiojozu.medicheck.domain.model.person.Person;
import com.studiojozu.medicheck.resource.uicomponent.dialog.ADialogView;
import com.studiojozu.medicheck.resource.uicomponent.listview.SingleSelectArrayAdapter;
import com.studiojozu.medicheck.resource.uicomponent.listview.SingleSelectItem;

import java.util.List;

abstract class APersonSelectActivity extends AMainActivity {

    @Nullable
    private SingleSelectArrayAdapter mSelectPersonAdapter = null;
    @Nullable
    private OnSelectedPersonListener mOnSelectedPersonListener = null;

    void initPersonSelect() {
        findViewById(R.id.person_select).setOnClickListener(createPersonTextClickListener());
    }

    private void showPersonSelectorDialog() {
        initSelectPersonAdapter();
        if (mSelectPersonAdapter == null) return;

        mOnSelectedPersonListener = createOnSelectedPersonListener();
        showSingleSelectorDialog(mSelectPersonAdapter, createPersonItemClickListener(), createPersonDialogCloseListener());
    }

    private void initSelectPersonAdapter() {
        PersonSelectService personSelectService = new PersonSelectService(getApplicationContext(), true);
        mSelectPersonAdapter = personSelectService.getPersonSelectAdapter();
    }

    private void finalSelectPerson() {
        if (mSelectPersonAdapter == null)
            return;

        mSelectPersonAdapter.recycle();
    }

    @Nullable
    abstract OnSelectedPersonListener createOnSelectedPersonListener();

    @NonNull
    private AdapterView.OnItemClickListener createPersonItemClickListener() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if (mSelectPersonAdapter == null) return;

                List<SingleSelectItem> itemList = mSelectPersonAdapter.getItemList();
                if (itemList.size() == 0) return;

                SingleSelectItem item = itemList.get(position);
                Person person = (Person) item.getTag();
                if (person == null) return;

                ((TextView) findViewById(R.id.person_select)).setText(person.getDisplayPersonName());
                if (mOnSelectedPersonListener != null)
                    mOnSelectedPersonListener.onSelectedPerson(person);
            }
        };
    }

    @NonNull
    private ADialogView.OnCloseListener createPersonDialogCloseListener() {
        return new ADialogView.OnCloseListener() {
            @Override
            public void onClose() {
                finalSelectPerson();
            }
        };
    }

    @NonNull
    private View.OnClickListener createPersonTextClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPersonSelectorDialog();
            }
        };
    }

    interface OnSelectedPersonListener {
        void onSelectedPerson(@NonNull Person person);
    }
}
