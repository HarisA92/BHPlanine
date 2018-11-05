package com.bhplanine.user.graduationproject.Bjelasnica.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bhplanine.user.graduationproject.Bjelasnica.models.LiftTicketHolder;
import com.bhplanine.user.graduationproject.Bjelasnica.models.SkiResortHolder;
import com.bhplanine.user.graduationproject.R;

import java.util.ArrayList;
public class LiftTicketAdapter extends RecyclerView.Adapter<LiftTicketAdapter.ViewHolder> {

    private Context context;
    private ArrayList<LiftTicketHolder> list;

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

        String title = SkiResortHolder.getSkiResort().getMountain().getValue();
        String website = liftTicketsHolder.getNapomena_web_stranica();
        final String URL = "<a href=" + website + ">" + liftTicketsHolder.getOpis_web_stranice() + title + "</a>";

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
        holder.napomena_web_stranica.setMovementMethod(LinkMovementMethod.getInstance());
        holder.napomena_web_stranica.setText(Html.fromHtml(URL));
        holder.napomena_web_stranica.setClickable(true);
        holder.dodatno_mjesto_odrasli1.setText(liftTicketsHolder.getDodatno_odrasli1());
        holder.dodatno_mjesto_odrasli2.setText(liftTicketsHolder.getDodatno_odrasli2());
        holder.dodatno_mjesto_odrasli3.setText(liftTicketsHolder.getDodatno_odrasli3());


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
        holder.dodatno_mjesto_djeca1.setText(liftTicketsHolder.getDjeca_dodatno1());
        holder.dodatno_mjesto_djeca2.setText(liftTicketsHolder.getDjeca_dodatno2());
        holder.dodatno_mjesto_djeca3.setText(liftTicketsHolder.getDjeca_dodatno3());


        holder.adults_title.setText(liftTicketsHolder.getOdrasli_naslov());
        holder.child_title.setText(liftTicketsHolder.getDjeca_naslov());
        holder.adults_season_title.setText(liftTicketsHolder.getOdrasli_naslov_sezonske());
        holder.child_season_title.setText(liftTicketsHolder.getDjeca_naslov_sezonske());

        if(!holder.dnevna_karta.getText().toString().matches("")){}else{holder.dnevna_karta.setVisibility(View.GONE);}
        if(!holder.cetiri_sata.getText().toString().matches("")){}else{holder.cetiri_sata.setVisibility(View.GONE);}
        if(!holder.cetvero_dnevna.getText().toString().matches("")){}else{holder.cetvero_dnevna.setVisibility(View.GONE);}
        if(!holder.deseto_dnevna.getText().toString().matches("")){}else{holder.deseto_dnevna.setVisibility(View.GONE);}
        if(!holder.desetodnevna_sezona.getText().toString().matches("")){}else{holder.desetodnevna_sezona.setVisibility(View.GONE);}
        if(!holder.dvodnevna.getText().toString().matches("")){}else{holder.dvodnevna.setVisibility(View.GONE);}
        if(!holder.trodnevna_karta.getText().toString().matches("")){}else{holder.trodnevna_karta.setVisibility(View.GONE);}
        if(!holder.tackice_sezona.getText().toString().matches("")){}else{holder.tackice_sezona.setVisibility(View.GONE);}
        if(!holder.petodnevna_sezona.getText().toString().matches("")){}else{holder.petodnevna_sezona.setVisibility(View.GONE);}
        if(!holder.pojedinacna_karta.getText().toString().matches("")){}else{holder.pojedinacna_karta.setVisibility(View.GONE);}
        if(!holder.sedmodnevna.getText().toString().matches("")){}else{holder.sedmodnevna.setVisibility(View.GONE);}
        if(!holder.petodnevna.getText().toString().matches("")){}else{holder.petodnevna.setVisibility(View.GONE);}
        if(!holder.djecija_cetiri_sata.getText().toString().matches("")){}else{holder.djecija_cetiri_sata.setVisibility(View.GONE); }
        if(!holder.djecija_cetvero_dnevna.getText().toString().matches("")){}else{holder.djecija_cetvero_dnevna.setVisibility(View.GONE);}
        if(!holder.djecija_deseto_dnevna.getText().toString().matches("")){}else{holder.djecija_deseto_dnevna.setVisibility(View.GONE);}
        if(!holder.djecija_desetodnevna_sezona.getText().toString().matches("")){}else{holder.djecija_desetodnevna_sezona.setVisibility(View.GONE);}
        if(!holder.djecija_dnevna_karta.getText().toString().matches("")){}else{holder.djecija_dnevna_karta.setVisibility(View.GONE);}
        if(!holder.djecija_dvodnevna.getText().toString().matches("")){}else{holder.djecija_dvodnevna.setVisibility(View.GONE);}
        if(!holder.djecija_petodnevna.getText().toString().matches("")){}else{holder.djecija_petodnevna.setVisibility(View.GONE);}
        if(!holder.djecija_petodnevna_sezona.getText().toString().matches("")){}else{holder.djecija_petodnevna_sezona.setVisibility(View.GONE);}
        if(!holder.djecija_pojedinacna_karta.getText().toString().matches("")){}else{holder.djecija_pojedinacna_karta.setVisibility(View.GONE);}
        if(!holder.djecija_sedmodnevna.getText().toString().matches("")){}else{holder.djecija_sedmodnevna.setVisibility(View.GONE);}
        if(!holder.djecija_trodnevna_karta.getText().toString().matches("")){}else{holder.djecija_trodnevna_karta.setVisibility(View.GONE);}
        if(!holder.sezonska.getText().toString().matches("")){}else{holder.sezonska.setVisibility(View.GONE);}
        if(!holder.predsezonska.getText().toString().matches("")){}else{holder.predsezonska.setVisibility(View.GONE);}
        if(!holder.babylift.getText().toString().matches("")){}else{holder.babylift.setVisibility(View.GONE);}
        if(!holder.nocno.getText().toString().matches("")){}else{holder.nocno.setVisibility(View.GONE);}
        if(!holder.porodicniPaket.getText().toString().matches("")){}else{holder.porodicniPaket.setVisibility(View.GONE);}
        if(!holder.djecija_babylift.getText().toString().matches("")){}else{holder.djecija_babylift.setVisibility(View.GONE);}
        if(!holder.djecija_porodicniPaket.getText().toString().matches("")){}else{holder.djecija_porodicniPaket.setVisibility(View.GONE);}
        if(!holder.djecija_nocno.getText().toString().matches("")){}else{holder.djecija_nocno.setVisibility(View.GONE);}
        if(!holder.djecija_sezonska.getText().toString().matches("")){}else{holder.djecija_sezonska.setVisibility(View.GONE);}
        if(!holder.djecija_porodicniPaket.getText().toString().matches("")){}else{holder.djecija_porodicniPaket.setVisibility(View.GONE);}
        if(!holder.djecija_predsezonska.getText().toString().matches("")){}else{holder.djecija_predsezonska.setVisibility(View.GONE);}
        if(!holder.adults_title.getText().toString().matches("")){}else{holder.adults_title.setVisibility(View.GONE);}
        if(!holder.child_title.getText().toString().matches("")){}else{holder.child_title.setVisibility(View.GONE);}
        if(!holder.adults_season_title.getText().toString().matches("")){}else{holder.adults_season_title.setVisibility(View.GONE);}
        if(!holder.child_season_title.getText().toString().matches("")){}else{holder.child_season_title.setVisibility(View.GONE);}
        if(!holder.dodatno_mjesto_djeca1.getText().toString().matches("")){}else{holder.dodatno_mjesto_djeca1.setVisibility(View.GONE);}
        if(!holder.dodatno_mjesto_djeca2.getText().toString().matches("")){}else{holder.dodatno_mjesto_djeca2.setVisibility(View.GONE);}
        if(!holder.dodatno_mjesto_djeca3.getText().toString().matches("")){}else{holder.dodatno_mjesto_djeca3.setVisibility(View.GONE);}
        if(!holder.dodatno_mjesto_odrasli1.getText().toString().matches("")){}else{holder.dodatno_mjesto_odrasli1.setVisibility(View.GONE);}
        if(!holder.dodatno_mjesto_odrasli2.getText().toString().matches("")){}else{holder.dodatno_mjesto_odrasli2.setVisibility(View.GONE);}
        if(!holder.dodatno_mjesto_odrasli3.getText().toString().matches("")){}else{holder.dodatno_mjesto_odrasli3.setVisibility(View.GONE);}
        if(!holder.napomena_text.getText().toString().matches("")){}else{holder.napomena_text.setVisibility(View.GONE);}
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView dnevna_karta, cetiri_sata, cetvero_dnevna, deseto_dnevna, desetodnevna_sezona, dvodnevna,
                petodnevna, petodnevna_sezona, pojedinacna_karta, sedmodnevna, tackice_sezona, trodnevna_karta,
                napomena_web_stranica, napomena_text, dodatno_mjesto_odrasli1, dodatno_mjesto_odrasli2, dodatno_mjesto_odrasli3;
        TextView djecija_dnevna_karta, djecija_cetiri_sata, djecija_cetvero_dnevna, djecija_deseto_dnevna,
                djecija_desetodnevna_sezona, djecija_dvodnevna, djecija_petodnevna, djecija_petodnevna_sezona,
                djecija_pojedinacna_karta, djecija_sedmodnevna, djecija_tackice_sezona, djecija_trodnevna_karta,
                dodatno_mjesto_djeca1, dodatno_mjesto_djeca2, dodatno_mjesto_djeca3;
        TextView sezonska, predsezonska, porodicniPaket, babylift, nocno;
        TextView djecija_sezonska, djecija_predsezonska, djecija_porodicniPaket, djecija_babylift, djecija_nocno;

        TextView adults_title, child_title, adults_season_title, child_season_title;

        ViewHolder(View itemView) {
            super(itemView);
            adults_title = itemView.findViewById(R.id.naslov_cjenovnik);
            child_title = itemView.findViewById(R.id.naslov_cjenovnik_djeca);
            adults_season_title = itemView.findViewById(R.id.naslov_cjenovnik_ostalo);
            child_season_title = itemView.findViewById(R.id.naslov_cjenovnik_ostalo_djeca);

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
            dodatno_mjesto_odrasli1 = itemView.findViewById(R.id.dodatno_mjesto1);
            dodatno_mjesto_odrasli2 = itemView.findViewById(R.id.dodatno_mjesto2);
            dodatno_mjesto_odrasli3 = itemView.findViewById(R.id.dodatno_mjesto3);

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
            dodatno_mjesto_djeca1 = itemView.findViewById(R.id.djeca_dodatno_mjesto1);
            dodatno_mjesto_djeca2 = itemView.findViewById(R.id.djeca_dodatno_mjesto2);
            dodatno_mjesto_djeca3 = itemView.findViewById(R.id.djeca_dodatno_mjesto3);

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
