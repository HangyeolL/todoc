package com.cleanup.todoc.model;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * <p>Models for project in which tasks are included.</p>
 *
 * @author Gaëtan HERFRAY
 */

@Entity
public class Project {
    /**
     * The unique identifier of the project
     */
    @PrimaryKey(autoGenerate = true)
    private long id;

    /**
     * The name of the project
     */
    @NonNull
    private String name;

    /**
     * The hex (ARGB) code of the color associated to the project
     */

    private int color;

    /**
     * Instantiates a new Project.
     * @param name  the name of the project to set
     * @param color the hex (ARGB) code of the color associated to the project to set
     */
    public Project(@NonNull String name, int color) {
        this.name = name;
        this.color = color;
    }

// I do this in ProjectDao
//    /**
//     * Returns all the projects of the application.
//     *
//     * @return all the projects of the application
//     */
//    @NonNull
//    public static Project[] getAllProjects() {
//        return new Project[]{
//                new Project(1L, "Projet Tartampion", 0xFFEADAD1),
//                new Project(2L, "Projet Lucidia", 0xFFB4CDBA),
//                new Project(3L, "Projet Circus", 0xFFA3CED2),
//        };
//    }


// I dont need cause I do this by ProjectDao
//    /**
//     * Returns the project with the given unique identifier, or null if no project with that
//     * identifier can be found.
//     *
//     * @param id the unique identifier of the project to return
//     * @return the project with the given unique identifier, or null if it has not been found
//     */
//    @Nullable
//    public static Project getProjectById(long id) {
//        for (Project project : getAllProjects()) {
//            if (project.id == id)
//                return project;
//        }
//        return null;
//    }

    /**
     * Returns the unique identifier of the project.
     *
     * @return the unique identifier of the project
     */
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    /**
     * Returns the name of the project.
     *
     * @return the name of the project
     */
    @NonNull
    public String getName() {
        return name;
    }

    /**
     * Returns the hex (ARGB) code of the color associated to the project.
     *
     * @return the hex (ARGB) code of the color associated to the project
     */

    public int getColor() {
        return color;
    }

//    @Override
//    @NonNull
//    public String toString() {
//        return getName();
//    }

}
