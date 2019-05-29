package com.example.architecturecomponents;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.architecturecomponents.Activities.EditNoteActivity;
import com.example.architecturecomponents.Activities.MainActivity;
import com.example.architecturecomponents.Notes.Note;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private final LayoutInflater layoutInflater;
    private Context context;
    private List<Note> list = new ArrayList<>();
    public Adapter(Context context,OnDeleteClickListener listener) {
        layoutInflater = LayoutInflater.from(context);
        deleteClickListener = listener;
        this.context=context;
    }

    // OLD METHODS
    public interface OnDeleteClickListener{
        void OnDeleteClickListener(Note mnote);
    }
    private OnDeleteClickListener deleteClickListener;


    public void setNote(List<Note> notes){
        list = notes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= layoutInflater.inflate(R.layout.content_main,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {

        if (list != null){
            Note note = list.get(position);
            holder.setData(note.getNote(),position);
            holder.setListner();
        }else holder.textView.setText("No notes");

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView textView;
        private int pos ;
        private ImageView edit,delete;
        public ViewHolder(View itemView) {
            super(itemView);
           textView = itemView.findViewById(R.id.cardTextview);
           edit = itemView.findViewById(R.id.cardEdit);
           delete = itemView.findViewById(R.id.cardDelete);
        }

        public void setData(String note, int position) {
          textView.setText(note);
          pos = position;
        }

        public void setListner() {

            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, EditNoteActivity.class);
                    intent.putExtra("note_id",list.get(pos).getId());
                    ((Activity)context).startActivityForResult(intent, MainActivity.UPDATE_CODE);
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(deleteClickListener != null){
                        deleteClickListener.OnDeleteClickListener(list.get(pos));
                    }
                }
            });
        }
    }
}