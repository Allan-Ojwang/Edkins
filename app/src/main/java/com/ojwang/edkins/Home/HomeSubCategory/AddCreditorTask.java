package com.ojwang.edkins.Home.HomeSubCategory;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.ojwang.edkins.R;

public class AddCreditorTask extends BottomSheetDialogFragment {
    public static final String TAG = "CREDITOR_ADD_TASK";

    public interface OnCreditorInputListener {
        void sendInputCreditor(String name, String reason, int amount);

    }

    public OnCreditorInputListener onCreditorInputListener;

    public EditText Evname, Evreason, Evamount;
    public Button save;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_creditor_task,container,false);
        Evname = view.findViewById(R.id.name);
        Evreason = view.findViewById(R.id.reason);
        Evamount = view.findViewById(R.id.amount);
        save = view.findViewById(R.id.saveBtn);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = Evname.getText().toString();
                String reason = Evreason.getText().toString();
                int amount = Integer.parseInt(Evamount.getText().toString());

                onCreditorInputListener.sendInputCreditor(name, reason,amount);
                getDialog().dismiss();

//                if (Evname.getText().toString().trim().isEmpty() || Evreason.getText().toString().trim().isEmpty()) {
//                    Toast.makeText(getContext(), "Please enter all information", Toast.LENGTH_SHORT).show();
//                    return;
//                }



//                else {
//
//                    if (id != -1) {
//                        onPaybillInputListener.sendUpdateInput(id, title, body);
//                        getDialog().dismiss();
//                    } else {
//                        onPaybillInputListener.sendInput(title, body);
//                        getDialog().dismiss();
//                    }
//                }
            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            onCreditorInputListener = (OnCreditorInputListener) getActivity();
        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach: ClassCastException: " + e.getMessage());
        }
    }
}
