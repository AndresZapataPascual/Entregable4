package com.andreszapata.entregable4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class UsuarioAdapter extends ArrayAdapter<Usuario> {

    private Context mContext;
    private List<Usuario> mUsuarioList;

    public UsuarioAdapter(Context context, List<Usuario> usuarioList) {
        super(context, 0, usuarioList);
        mContext = context;
        mUsuarioList = usuarioList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_usuario, parent, false);
            holder = new ViewHolder();
            holder.nombreTextView = convertView.findViewById(R.id.nombreTextView);
            holder.apellidoTextView = convertView.findViewById(R.id.apellidoTextView);
            holder.correoTextView = convertView.findViewById(R.id.correoTextView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Usuario usuario = mUsuarioList.get(position);
        holder.nombreTextView.setText(usuario.getNombre());
        holder.apellidoTextView.setText(usuario.getApellido());
        holder.correoTextView.setText(usuario.getCorreo());

        return convertView;
    }

    static class ViewHolder {
        TextView nombreTextView;
        TextView apellidoTextView;
        TextView correoTextView;
    }
}
