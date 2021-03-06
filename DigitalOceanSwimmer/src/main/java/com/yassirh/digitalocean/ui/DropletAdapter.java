package com.yassirh.digitalocean.ui;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yassirh.digitalocean.R;
import com.yassirh.digitalocean.model.Droplet;
import com.yassirh.digitalocean.model.Image;
import com.yassirh.digitalocean.model.Network;
import com.yassirh.digitalocean.model.Region;
import com.yassirh.digitalocean.utils.ApiHelper;


public class DropletAdapter extends BaseAdapter {

    private List<Droplet> data;
    private static LayoutInflater inflater = null;

    public DropletAdapter(Context context, List<Droplet> data) {
        this.data = data;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return data.get(position);
    }

    public long getItemId(int position) {
        return data.get(position).getId();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(R.layout.droplet_list_row, parent, false);

        final Droplet droplet = data.get(position);
        Image image = droplet.getImage();

        TextView nameTextView = vi.findViewById(R.id.nameTextView);
        ImageView statusImageView = vi.findViewById(R.id.statusImageView);
        ImageView distroImageView = vi.findViewById(R.id.distroImageView);
        TextView ipAddressTextView = vi.findViewById(R.id.ipAddressTextView);

        if (image != null) {
            distroImageView.setImageResource(ApiHelper.getDistributionLogo(image.getDistribution(), droplet.getStatus()));
        }

        nameTextView.setText(droplet.getName());
        if (droplet.getNetworks().size() > 0) {
            for (Network network : droplet.getNetworks()) {
                if (network.getType().equals("public")) {
                    ipAddressTextView.setText(network.getIpAddress());
                    break;
                }
            }
        }

        if (droplet.getStatus().equalsIgnoreCase("active")) {
            statusImageView.setImageResource(R.drawable.droplet_active);
        } else {
            statusImageView.setImageResource(R.drawable.droplet_off);
        }

        return vi;
    }
}