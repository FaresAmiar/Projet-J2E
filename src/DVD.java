package persistance;

import mediatek2020.items.*;

public class DVD implements Document {
    @Override
    public void reserver(Utilisateur utilisateur) throws ReservationException {

    }

    @Override
    public void emprunter(Utilisateur utilisateur) throws EmpruntException {

    }

    @Override
    public void rendre(Utilisateur utilisateur) throws RetourException {

    }

    @Override
    public Object[] data() {
        return new Object[0];
    }
}
