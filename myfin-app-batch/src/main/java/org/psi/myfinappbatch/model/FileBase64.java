package org.psi.myfinappbatch.model;

public class FileBase64 {

    private String base64;

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }


    public FileBase64() {
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((base64 == null) ? 0 : base64.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        FileBase64 other = (FileBase64) obj;
        if (base64 == null) {
            if (other.base64 != null)
                return false;
        } else if (!base64.equals(other.base64))
            return false;
        return true;
    }

    

}
