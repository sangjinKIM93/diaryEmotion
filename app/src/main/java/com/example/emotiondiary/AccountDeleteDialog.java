package com.example.emotiondiary;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AccountDeleteDialog extends DialogFragment {

    View view;
    TextView BtnYes, BtnNo;

    interface AccountDeleteDialogInterface{
        void delete();
        void cancelDelete();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.accountdelete_dialog, container);

        // 레이아웃 XML과 뷰 변수 연결

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    class addAccountDeleteDialogInterface implements AccountDeleteDialogInterface{

        @Override
        public void delete() {

        }

        @Override
        public void cancelDelete() {

        }
    }

    private void setupListener(){
        BtnYes = view.findViewById(R.id.accountDelete_yes);
        BtnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        BtnNo = view.findViewById(R.id.accountDelete_no);
        BtnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
