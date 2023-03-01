package Entity;

public class Reservation {
        private int id;

        private int evenementId;
        private int idUsers;

        public Reservation() {
            // Constructeur par défaut
        }

        public Reservation(int id, int evenementId, int idUsers) {
            this.id = id;
            this.evenementId = evenementId;
            this.idUsers = idUsers;
        }

        public Reservation( int evenementId, int idUsers) {
            this.evenementId = evenementId;
            this.idUsers = idUsers;
        }


        // Getters
        public int getId() {
            return id;
        }


        public int getEvenementId() {
            return evenementId;
        }

        public int getIdUsers() {
            return idUsers;
        }

        // Setters
        public void setId(int id) {
            this.id = id;
        }


        public void setEvenementId(int evenementId) {
            this.evenementId = evenementId;
        }

        public void setIdUsers(int idUsers) {
            this.idUsers = idUsers;
        }

        // ToString
        @Override
        public String toString() {
            return "Reservation [id=" + id + ", date=" + ", nombrePersonnes="  + ", evenementId="
                    + evenementId + ", idUsers=" + idUsers + "]";
        }

    }


