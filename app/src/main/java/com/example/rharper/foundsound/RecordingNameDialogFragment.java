package com.example.rharper.foundsound;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class RecordingNameDialogFragment extends DialogFragment {

    public interface RecordingNameDialogListener{
        void onDialogPositiveClick(String name);
    }

    RecordingNameDialogListener listener;
    String mName;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (RecordingNameDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(getActivity().toString()
                    + " must implement NoticeDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        final View customView = inflater.inflate(R.layout.name_dialog_layout, null);
        EditText et = customView.findViewById(R.id.recordingName);


                // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(customView).setTitle("Please enter a recording name")
                .setPositiveButton(R.string.save_message, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String name = et.getText().toString();
                        listener.onDialogPositiveClick(name);
                    }
                })
                .setNegativeButton(R.string.cancel_message, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        RecordingNameDialogFragment.this.getDialog().cancel();
                    }
                });
                return builder.create();
    }

}
