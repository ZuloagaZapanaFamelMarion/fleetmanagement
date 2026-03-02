package service;

import model.Taxi;
import model.Trajectory;
import model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Servicio de persistencia: guarda y recupera el estado del sistema
 * en archivos .dat mediante serialización Java.
 */
public class PersistenceService {

    private static final String DEFAULT_FILE = "fleet_state.dat";

    /**
     * Guarda usuarios, taxis y trayectorias en un archivo .dat.
     */
    public void saveState(List<User> users, List<Taxi> taxis, List<Trajectory> trajectories)
            throws IOException {
        saveState(users, taxis, trajectories, DEFAULT_FILE);
    }

    public void saveState(List<User> users, List<Taxi> taxis, List<Trajectory> trajectories,
                          String fileName) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(users != null ? new ArrayList<>(users) : new ArrayList<>());
            oos.writeObject(taxis != null ? new ArrayList<>(taxis) : new ArrayList<>());
            oos.writeObject(trajectories != null ? new ArrayList<>(trajectories) : new ArrayList<>());
        }
    }

    /**
     * Carga el estado desde un archivo .dat.
     * @return estado cargado (listas de usuarios, taxis, trayectorias)
     */
    @SuppressWarnings("unchecked")
    public LoadedState loadState(String fileName) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            List<User> users = (List<User>) ois.readObject();
            List<Taxi> taxis = (List<Taxi>) ois.readObject();
            List<Trajectory> trajectories = (List<Trajectory>) ois.readObject();
            return new LoadedState(users, taxis, trajectories);
        }
    }

    public LoadedState loadState() throws IOException, ClassNotFoundException {
        return loadState(DEFAULT_FILE);
    }

    /**
     * Contenedor del estado cargado desde disco.
     */
    public static class LoadedState {
        private final List<User> users;
        private final List<Taxi> taxis;
        private final List<Trajectory> trajectories;

        public LoadedState(List<User> users, List<Taxi> taxis, List<Trajectory> trajectories) {
            this.users = users != null ? users : new ArrayList<>();
            this.taxis = taxis != null ? taxis : new ArrayList<>();
            this.trajectories = trajectories != null ? trajectories : new ArrayList<>();
        }

        public List<User> getUsers() { return users; }
        public List<Taxi> getTaxis() { return taxis; }
        public List<Trajectory> getTrajectories() { return trajectories; }
    }
}