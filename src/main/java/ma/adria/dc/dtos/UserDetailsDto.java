package ma.adria.dc.dtos;

import java.util.List;

public class UserDetailsDto {

    private String username;
    private String statut;
    private String codeLangue;
    private Long contratId;
    private List<String> authorities;

    public UserDetailsDto() {
    }

    public UserDetailsDto(String username, String statut, String codeLangue, Long contratId, List<String> authorities) {
        this.username = username;
        this.statut = statut;
        this.codeLangue = codeLangue;
        this.contratId = contratId;
        this.authorities = authorities;
    }

    // Getters et Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getCodeLangue() {
        return codeLangue;
    }

    public void setCodeLangue(String codeLangue) {
        this.codeLangue = codeLangue;
    }

    public Long getContratId() {
        return contratId;
    }

    public void setContratId(Long contratId) {
        this.contratId = contratId;
    }

    public List<String> getAuthorities() {
        return authorities;
    }



    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    }

    @Override
    public String toString() {
        return "UserDetailsDto{" +
                "username='" + username + '\'' +
                ", statut='" + statut + '\'' +
                ", codeLangue='" + codeLangue + '\'' +
                ", contratId=" + contratId +
                ", authorities=" + authorities +
                '}';
    }
}
