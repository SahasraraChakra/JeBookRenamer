/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EBookRenamerJ;

/**
 *
 * @author Jeevan Kumar
 */
public class LanguageItem {
    private final String id;
    private final String name;

    public LanguageItem(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String toString() {
        return name; // displayed in combo
    }
}
