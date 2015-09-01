package tes.volley.gsonparser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kucing Imut on 8/21/15.
 */


public class ArrayBerita {


    public int code = 0;
    public List<Berita> result = new ArrayList<>();

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<Berita> getResult() {
        return result;
    }

    public void setResult(List<Berita> result) {
        this.result = result;
    }
}
