package gaaynako.transpay.crudandroidretrofit.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import gaaynako.transpay.crudandroidretrofit.R;
import gaaynako.transpay.crudandroidretrofit.activities.ReadPersonneActivity;
import gaaynako.transpay.crudandroidretrofit.activities.UpdateActivity;
import gaaynako.transpay.crudandroidretrofit.models.Personne;

public class PersonneRvAdapter extends RecyclerView.Adapter<PersonneRvAdapter.ViewHolder> {

    private List<Personne> personneList;
    private Context context;

    public PersonneRvAdapter(List<Personne> personneList, Context context) {
        this.personneList = personneList;
        this.context = context;
    }

    @NonNull
    @Override
    public PersonneRvAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.personne_rv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonneRvAdapter.ViewHolder holder, int position) {
        Personne personne = personneList.get(position);
        holder.rvAdresse.setText(personne.getAdressePersonne());
        holder.rvName.setText(personne.getNomPrenomPersonne());
        holder.rvEmail.setText(personne.getEmailPersonne());
        holder.rvPhone.setText(personne.getTelPersonne());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are creating a new intent.
                Intent i = new Intent(context, UpdateActivity.class);
                // on below line we are passing all the data to new activity.
                i.putExtra("nomPrenomPersonne", personne.getNomPrenomPersonne());
                i.putExtra("adressePersonne", personne.getAdressePersonne());
                i.putExtra("telPersonne", personne.getTelPersonne());
                i.putExtra("emailPersonne", personne.getEmailPersonne());
                i.putExtra("dateNaissancePersonne",personne.getDateNaissancePersonne());
                i.putExtra("idPersonne", personne.getIdPersonne());
                i.putExtra("id", personne.getId());
                // on below line we are starting a new activity.
                context.startActivity(i);
//                ReadPersonneActivity.read_activity.finish();

            }
        });
    }

    @Override
    public int getItemCount() {
        return personneList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // creating variables for our text views.
        private TextView rvName, rvEmail, rvPhone, rvAdresse;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views
            rvAdresse = itemView.findViewById(R.id.idRvAdresse);
            rvName = itemView.findViewById(R.id.idRvName);
            rvEmail = itemView.findViewById(R.id.idRvEmail);
            rvPhone = itemView.findViewById(R.id.idRvPhone);
        }
    }
}
