package id.usk.service.model;

public class Pesan {
    private String id, name, alamat, jasa;
    public Pesan(){

    }
    public Pesan(String name, String alamat, String jasa){

        this.name = name;
        this.alamat = alamat;
        this.jasa = jasa;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }


    public String getJasa() {
        return jasa;
    }
    public void setJasa(String jasa) {
        this.jasa = jasa;
    }


}
