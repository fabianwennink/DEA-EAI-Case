package nl.fabianwennink.dea.database.dao;

import nl.fabianwennink.dea.database.entities.Playlist;

import java.util.List;
import java.util.logging.Level;

public class PlaylistDAO extends BaseDAO {

    private static final String GET_ALL_PLAYLISTS_QUERY = "SELECT playlist FROM Playlist playlist";
    private static final String GET_TOTAL_DURATION_QUERY = "SELECT SUM(track.duration) FROM Track track";
    private static final String IS_OWNED_BY_USER_QUERY = "SELECT 1 FROM Playlist playlist WHERE playlist.id = :playlist_id AND playlist.ownerId = :owner_id";

    public Playlist getSingle(int id) {
        return entityManager.find(Playlist.class, id);
    }

    public List<Playlist> getAll() {
        return entityManager.createQuery(GET_ALL_PLAYLISTS_QUERY).getResultList();
    }

    public boolean persist(Playlist playlist) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(playlist);
            entityManager.getTransaction().commit();
        } catch(Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to execute query: " + e.getMessage());
            return false;
        }

        return true;
    }

    public boolean delete(Playlist playlist) {
        try {
            entityManager.getTransaction().begin();
            playlist = entityManager.merge(playlist); // attach entity to manager, cannot delete detachted entities.
            entityManager.remove(playlist);
            entityManager.getTransaction().commit();
        } catch(Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to execute query: " + e.getMessage());
            return false;
        }

        return true;
    }

    public int getTotalDuration() {
        // Fetch the aggregate value in a generic object and cast it to int if valid
        Object results = entityManager.createQuery(GET_TOTAL_DURATION_QUERY).getSingleResult();

        return (results instanceof Integer) ? (int)results : 0;
    }

    public boolean isOwnedByUser(int playlistId, int userId) {
        Object results = entityManager.createQuery(IS_OWNED_BY_USER_QUERY)
                .setParameter("playlist_id", playlistId)
                .setParameter("owner_id", userId).getSingleResult();

        return results != null;
    }
}
