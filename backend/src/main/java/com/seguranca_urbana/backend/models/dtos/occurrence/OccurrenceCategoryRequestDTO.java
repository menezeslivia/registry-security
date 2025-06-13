package com.seguranca_urbana.backend.models.dtos.occurrence;

public class OccurrenceCategoryRequestDTO {
    private String name;
    private String description;

    public OccurrenceCategoryRequestDTO() {}

    public OccurrenceCategoryRequestDTO(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    @Override
    public String toString() {
        return "OccurrenceCategoryRequestDTO(name=" + name + ", description=" + description + ")";
    }
}