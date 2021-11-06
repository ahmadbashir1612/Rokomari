package com.project.rokomari.ui;

import static android.content.Context.INPUT_METHOD_SERVICE;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputLayout;
import com.project.rokomari.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PhoneEmailUrlSaveFragmentDialog extends DialogFragment {

    @BindView(R.id.edit_text)
    EditText etOption;

    @BindView(R.id.taskNameTextField)
    TextInputLayout taskNameTextField;

    @BindView(R.id.image_view)
    ImageView ivOption;

    @BindView(R.id.btnSave)
    Button btnSave;

    String text, option;


    public OnSaveListener mOnSaveListener;

    public PhoneEmailUrlSaveFragmentDialog() {

    }


    public interface OnSaveListener {

        void didSave(String option,String text);

    }


    public static PhoneEmailUrlSaveFragmentDialog newInstance(String title, String text, String option) {
        PhoneEmailUrlSaveFragmentDialog frag = new PhoneEmailUrlSaveFragmentDialog();
        Bundle args = new Bundle();
        args.putString("Filter", title);
        args.putString("Text", text);
        args.putString("Option", option);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_fragment_url_email_phone, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.rounded_dialog);
        String title = getArguments().getString("Filter", "");
        text = getArguments().getString("Text", "");
        option = getArguments().getString("Option", "");
        getDialog().setTitle(title);
        setCancelable(true);

        etOption.setText(text);
        if (option.equals("email")) {
            ivOption.setImageDrawable(getResources().getDrawable(R.drawable.ic_email));
            taskNameTextField.setHint("Enter E-mail");
            btnSave.setText("Save E-Mail");
            etOption.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        } else if (option.equals("phone")) {
            ivOption.setImageDrawable(getResources().getDrawable(R.drawable.ic_phone));
            taskNameTextField.setHint("Enter phone");
            btnSave.setText("Save phone");
            etOption.setInputType(InputType.TYPE_CLASS_PHONE);
        } else {
            ivOption.setImageDrawable(getResources().getDrawable(R.drawable.ic_url));
            taskNameTextField.setHint("Enter url");
            btnSave.setText("Save URL");
            etOption.setInputType(InputType.TYPE_TEXT_VARIATION_URI);
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnSaveListener.didSave(option,etOption.getText().toString());
                dismiss();

            }
        });

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mOnSaveListener = (OnSaveListener) getActivity();

    }


    @Override
    public void onResume() {
        // Sets the height and the width of the DialogFragment
        super.onResume();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }


}

