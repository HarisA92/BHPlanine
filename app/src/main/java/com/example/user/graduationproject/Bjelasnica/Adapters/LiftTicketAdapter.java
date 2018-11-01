package com.example.user.graduationproject.Bjelasnica.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.graduationproject.Bjelasnica.Utils.LiftTicketHolder;
import com.example.user.graduationproject.Bjelasnica.Utils.SkiResortHolder;
import com.example.user.graduationproject.R;

import java.util.ArrayList;

public class LiftTicketAdapter extends RecyclerView.Adapter<LiftTicketAdapter.ViewHolder> {

    private Context context;
    private ArrayList<LiftTicketHolder> list;
    private String title;
    private String url;

    public LiftTicketAdapter(Context context, ArrayList<LiftTicketHolder> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public LiftTicketAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.lift_ticket_price, parent, false);
        return new LiftTicketAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final LiftTicketAdapter.ViewHolder holder, int position) {
        LiftTicketHolder liftTicketsHolder = list.get(position);
        holder.dnevna_karta.setText(liftTicketsHolder.getDnevna_karta());
        holder.cetiri_sata.setText(liftTicketsHolder.getCetirisata());
        holder.cetvero_dnevna.setText(liftTicketsHolder.getCetverodnevna());
        holder.deseto_dnevna.setText(liftTicketsHolder.getDesetodnevna());
        holder.desetodnevna_sezona.setText(liftTicketsHolder.getDesetodnevnaSezona());
        holder.dvodnevna.setText(liftTicketsHolder.getDvodnevna());
        holder.trodnevna_karta.setText(liftTicketsHolder.getTrodnevna());
        holder.petodnevna_sezona.setText(liftTicketsHolder.getPetodnevnaSezona());
        holder.tackice_sezona.setText(liftTicketsHolder.getTackice());
        holder.pojedinacna_karta.setText(liftTicketsHolder.getPojedinacna_karta());
        holder.sedmodnevna.setText(liftTicketsHolder.getSedmodnevna());
        holder.petodnevna.setText(liftTicketsHolder.getPetodnevna());
        holder.napomena_text.setText(liftTicketsHolder.getText_napomena());

        title = SkiResortHolder.getSkiResort().getMountain().getValue();
        url = liftTicketsHolder.getNapomena_web_stranica();
        final String ulr1 = "<a href=" + url + ">" + title + "</a>";
        holder.napomena_web_stranica.setMovementMethod(LinkMovementMethod.getInstance());
        holder.napomena_web_stranica.setText(Html.fromHtml(ulr1));
        holder.napomena_web_stranica.setClickable(true);

        holder.djecija_cetiri_sata.setText(liftTicketsHolder.getDjeca_cetirisata());
        holder.djecija_cetvero_dnevna.setText(liftTicketsHolder.getDjeca_cetverodnevna());
        holder.djecija_deseto_dnevna.setText(liftTicketsHolder.getDjeca_desetodnevna());
        holder.djecija_desetodnevna_sezona.setText(liftTicketsHolder.getDjeca_desetodnevnaSezona());
        holder.djecija_dnevna_karta.setText(liftTicketsHolder.getDjeca_dnevna_karta());
        holder.djecija_dvodnevna.setText(liftTicketsHolder.getDjeca_dvodnevna());
        holder.djecija_petodnevna.setText(liftTicketsHolder.getDjeca_petodnevna());
        holder.djecija_petodnevna_sezona.setText(liftTicketsHolder.getDjeca_petodnevnaSezona());
        holder.djecija_pojedinacna_karta.setText(liftTicketsHolder.getDjeca_pojedinacna_karta());
        holder.djecija_sedmodnevna.setText(liftTicketsHolder.getDjeca_sedmodnevna());
        holder.djecija_tackice_sezona.setText(liftTicketsHolder.getDjeca_tackice());
        holder.djecija_trodnevna_karta.setText(liftTicketsHolder.getDjeca_trodnevna());

        if(!holder.djecija_predsezonska.getText().toString().matches("")){

        }
        else if(holder.djecija_sezonska.getText().toString().matches("")){

        }
        else if(holder.djecija_porodicniPaket.getText().toString().matches("")){

        }
        else{
            holder.djecija_predsezonska.setVisibility(View.GONE);
        }

        holder.sezonska.setText(liftTicketsHolder.getSezonska());
        holder.predsezonska.setText(liftTicketsHolder.getPredsezonska());
        holder.babylift.setText(liftTicketsHolder.getBabylift());
        holder.nocno.setText(liftTicketsHolder.getNocno());
        holder.porodicniPaket.setText(liftTicketsHolder.getPorodicni_paket());

        holder.djecija_babylift.setText(liftTicketsHolder.getDjeca_babylift());
        holder.djecija_porodicniPaket.setText(liftTicketsHolder.getDjeca_porodicni_paket());
        holder.djecija_nocno.setText(liftTicketsHolder.getDjeca_nocno());
        holder.djecija_sezonska.setText(liftTicketsHolder.getDjeca_sezonska());
        holder.djecija_predsezonska.setText(liftTicketsHolder.getDjeca_predsezonska());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView dnevna_karta, cetiri_sata, cetvero_dnevna, deseto_dnevna, desetodnevna_sezona, dvodnevna,
                petodnevna, petodnevna_sezona, pojedinacna_karta, sedmodnevna, tackice_sezona, trodnevna_karta,
                napomena_web_stranica, napomena_text;
        TextView djecija_dnevna_karta, djecija_cetiri_sata, djecija_cetvero_dnevna, djecija_deseto_dnevna,
                djecija_desetodnevna_sezona, djecija_dvodnevna, djecija_petodnevna, djecija_petodnevna_sezona,
                djecija_pojedinacna_karta, djecija_sedmodnevna, djecija_tackice_sezona, djecija_trodnevna_karta;
        TextView sezonska, predsezonska, porodicniPaket, babylift, nocno;
        TextView djecija_sezonska, djecija_predsezonska, djecija_porodicniPaket, djecija_babylift, djecija_nocno;

        public ViewHolder(View itemView) {
            super(itemView);
            dnevna_karta = itemView.findViewById(R.id.dnevna);
            cetiri_sata = itemView.findViewById(R.id.cetirisata);
            cetvero_dnevna = itemView.findViewById(R.id.cetverodnevna);
            deseto_dnevna = itemView.findViewById(R.id.desetodnevna);
            desetodnevna_sezona = itemView.findViewById(R.id.desetodnevnaSezona);
            dvodnevna = itemView.findViewById(R.id.dvodnevna);
            petodnevna = itemView.findViewById(R.id.petodnevna);
            petodnevna_sezona = itemView.findViewById(R.id.petodnevnaSezona);
            pojedinacna_karta = itemView.findViewById(R.id.pojedinacna_karta);
            sedmodnevna = itemView.findViewById(R.id.sedmodnevna);
            tackice_sezona = itemView.findViewById(R.id.tackice);
            trodnevna_karta = itemView.findViewById(R.id.trodnevna);
            napomena_web_stranica = itemView.findViewById(R.id.web_stranica);
            napomena_text = itemView.findViewById(R.id.text_cjenovnik);

            djecija_dnevna_karta = itemView.findViewById(R.id.djeca_dnevna);
            djecija_cetiri_sata = itemView.findViewById(R.id.djeca_cetirisata);
            djecija_cetvero_dnevna = itemView.findViewById(R.id.djeca_cetverodnevna);
            djecija_deseto_dnevna = itemView.findViewById(R.id.djeca_desetodnevna);
            djecija_desetodnevna_sezona = itemView.findViewById(R.id.djeca_desetodnevnaSezona);
            djecija_dvodnevna = itemView.findViewById(R.id.djeca_dvodnevna);
            djecija_petodnevna = itemView.findViewById(R.id.djeca_petodnevna);
            djecija_petodnevna_sezona = itemView.findViewById(R.id.djeca_petodnevnaSezona);
            djecija_pojedinacna_karta = itemView.findViewById(R.id.djeca_pojedinacna_karta);
            djecija_sedmodnevna = itemView.findViewById(R.id.djeca_sedmodnevna);
            djecija_tackice_sezona = itemView.findViewById(R.id.djeca_tackice);
            djecija_trodnevna_karta = itemView.findViewById(R.id.djeca_trodnevna);

            sezonska = itemView.findViewById(R.id.sezonska_karta);
            predsezonska = itemView.findViewById(R.id.predsezonska_karta);
            porodicniPaket = itemView.findViewById(R.id.porodicni_paket);
            babylift = itemView.findViewById(R.id.baby_lift);
            nocno = itemView.findViewById(R.id.nocno_skijanje);

            djecija_sezonska = itemView.findViewById(R.id.sezonska_karta_djeca);
            djecija_predsezonska = itemView.findViewById(R.id.predsezonska_karta_djeca);
            djecija_porodicniPaket = itemView.findViewById(R.id.porodicni_paket_djeca);
            djecija_babylift = itemView.findViewById(R.id.baby_lift_djeca);
            djecija_nocno = itemView.findViewById(R.id.nocno_djeca);

        }
    }
}
