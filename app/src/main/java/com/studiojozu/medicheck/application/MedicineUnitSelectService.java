package com.studiojozu.medicheck.application;

import android.content.Context;
import android.support.annotation.NonNull;

import com.studiojozu.medicheck.R;
import com.studiojozu.medicheck.domain.model.medicine.MedicineUnit;
import com.studiojozu.medicheck.domain.model.medicine.repository.MedicineUnitRepository;
import com.studiojozu.medicheck.infrastructure.InfrastructureRegistry;
import com.studiojozu.medicheck.resource.uicomponent.listview.SingleSelectArrayAdapter;
import com.studiojozu.medicheck.resource.uicomponent.listview.SingleSelectItem;

import java.util.ArrayList;
import java.util.List;

public class MedicineUnitSelectService {
    @NonNull
    private final Context mContext;
    private final boolean mUseAddNewMedicineUnit;
    private final MedicineUnitRepository mMedicineUnitRepository = InfrastructureRegistry.getMedicineUnitRepository();

    public MedicineUnitSelectService(@NonNull Context context, boolean useAddNewMedicineUnit) {
        mContext = context;
        mUseAddNewMedicineUnit = useAddNewMedicineUnit;
    }

    @NonNull
    public SingleSelectArrayAdapter getSelectAdapter() {
        List<SingleSelectItem> itemList = getSelectItemList();
        return new SingleSelectArrayAdapter(mContext, itemList, false);
    }

    private List<SingleSelectItem> getSelectItemList() {
        List<SingleSelectItem> itemList = new ArrayList<>();
        addMedicineUnit(itemList);
        addNewMedicineUnit(itemList);
        return itemList;
    }

    private void addMedicineUnit(@NonNull List<SingleSelectItem> listItem) {
        List<MedicineUnit> medicineUnitList = new ArrayList<>(mMedicineUnitRepository.findAll(mContext));
        for (MedicineUnit medicineUnit : medicineUnitList) {
            SingleSelectItem<MedicineUnit> item = new SingleSelectItem.Builder<MedicineUnit>(medicineUnit.getDisplayValue())
                    .setTag(medicineUnit)
                    .build();
            listItem.add(item);
        }
    }

    private void addNewMedicineUnit(@NonNull List<SingleSelectItem> listItem) {
        if (!mUseAddNewMedicineUnit) return;

        String text = mContext.getResources().getString(R.string.label_add_short);
        SingleSelectItem<MedicineUnit> item = new SingleSelectItem.Builder<MedicineUnit>(text)
                .setTag(new MedicineUnit())
                .build();
        listItem.add(item);
    }


}
