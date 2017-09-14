package com.studiojozu.medicheck.application;

import android.content.Context;
import android.support.annotation.NonNull;

import com.studiojozu.medicheck.R;
import com.studiojozu.medicheck.domain.model.person.Person;
import com.studiojozu.medicheck.domain.model.person.PersonRepository;
import com.studiojozu.medicheck.infrastructure.adapter.PersistenceAdapter;
import com.studiojozu.medicheck.resource.uicomponent.listview.SingleSelectArrayAdapter;
import com.studiojozu.medicheck.resource.uicomponent.listview.SingleSelectItem;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class PersonSelectService {
    @NonNull
    private final Context mContext;
    @NonNull
    private final PersonRepository mPersonRepository = PersistenceAdapter.getPersonRepository();
    private final boolean mUseUserAdd;

    public PersonSelectService(@NonNull Context context, boolean useUserAdd) {
        mContext = context;
        mUseUserAdd = useUserAdd;
    }

    @NonNull
    public SingleSelectArrayAdapter getPersonSelectAdapter() {
        List<SingleSelectItem> itemList = getPersonSelectItemList();
        return new SingleSelectArrayAdapter(mContext, itemList, false, R.mipmap.person_no_image);
    }

    private List<SingleSelectItem> getPersonSelectItemList() {
        List<SingleSelectItem> itemList = new ArrayList<>();
        addPerson(itemList);
        addUserAdd(itemList);
        return itemList;
    }

    private void addPerson(@NonNull List<SingleSelectItem> listItem) {
        List<Person> personList = mPersonRepository.findAll(mContext);
        if (personList == null) return;

        for (Person person : personList) {
            SingleSelectItem<Person> item = new SingleSelectItem.Builder<Person>(person.getDisplayPersonName())
                    .setImageFileUri(person.getPhotoUri())
                    .setTag(person)
                    .build();

            listItem.add(item);
        }
    }

    private void addUserAdd(@NonNull List<SingleSelectItem> listItem) {
        if (!mUseUserAdd) return;

        String text = mContext.getResources().getString(R.string.label_add);
        SingleSelectItem<Person> item = new SingleSelectItem.Builder<Person>(text)
                .setImageResourceId(R.mipmap.add_person)
                .setTag(new Person())
                .build();

        listItem.add(item);
    }


}
