package com.studiojozu.medicheck.application;

import android.content.Context;
import android.support.annotation.NonNull;

import com.studiojozu.medicheck.R;
import com.studiojozu.medicheck.domain.model.person.Person;
import com.studiojozu.medicheck.domain.model.person.PersonRepository;
import com.studiojozu.medicheck.infrastructure.adapter.PersistenceAdapter;
import com.studiojozu.medicheck.resource.uicomponent.listview.ImageSingleSelectArrayAdapter;
import com.studiojozu.medicheck.resource.uicomponent.listview.ImageSingleSelectItem;

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
    public ImageSingleSelectArrayAdapter getPersonSelectAdapter() {
        List<ImageSingleSelectItem> itemList = getPersonSelectItemList();
        return new ImageSingleSelectArrayAdapter(mContext, itemList, R.mipmap.person_no_image);
    }

    private List<ImageSingleSelectItem> getPersonSelectItemList() {
        List<ImageSingleSelectItem> itemList = new ArrayList<>();
        addPerson(itemList);
        addUserAdd(itemList);
        return itemList;
    }

    private void addPerson(@NonNull List<ImageSingleSelectItem> listItem) {
        List<Person> personList = mPersonRepository.findAll(mContext);
        if (personList == null) return;

        for (Person person : personList) {
            ImageSingleSelectItem<Person> item = new ImageSingleSelectItem<>(person.getDisplayPersonName(), person.getPhotoUri(), person);
            listItem.add(item);
        }
    }

    private void addUserAdd(@NonNull List<ImageSingleSelectItem> listItem) {
        if (!mUseUserAdd) return;

        String text = mContext.getResources().getString(R.string.label_add);
        ImageSingleSelectItem<Person> item = new ImageSingleSelectItem<>(text, R.mipmap.add_person, new Person());
        listItem.add(item);
    }


}
