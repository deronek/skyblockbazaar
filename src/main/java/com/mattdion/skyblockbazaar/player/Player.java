package com.mattdion.skyblockbazaar.player;

import java.util.Objects;
import java.util.UUID;

public class Player {
    private String name;
    private UUID id;

    public Player(String name, String id) {
        this.name = name;

        // parsing UUID without dashes
        String id_parsed = id.replaceAll(
                "(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})",
                "$1-$2-$3-$4-$5");
        this.id = UUID.fromString(id_parsed);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;
        Player player = (Player) o;
        return Objects.equals(getId(), player.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
