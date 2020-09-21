package model.dto;

import model.dao.DBException;
import model.dao.InseratDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class InseratService {

    private static InseratService inseratService;
    private List<InseratDTO> alleInserate = new ArrayList<>();

    public InseratService() {
        try {
            alleInserate = InseratDAO.getInstance().getAll();
        } catch (DBException e) {
            e.printStackTrace();
        }
    }

    public static InseratService getInstance() {
        if (inseratService == null) {
            inseratService = new InseratService();
        }
        return inseratService;
    }

    public Stream<String> fetch(String filter, int offset, int limit) {
        return alleInserate.stream()
                .filter(inserate -> filter == null || inserate.toString()
                        .toLowerCase().startsWith(filter.toLowerCase()))
                .skip(offset).limit(limit).map(i -> i.toString());
    }

    public int count(String filter) {
        return (int) alleInserate.stream()
                .filter(inserat -> filter == null || inserat.toString()
                        .toLowerCase().startsWith(filter.toLowerCase()))
                .count();
    }

    public Stream<InseratDTO> fetchPage(String filter, int page, int pageSize) {
        return alleInserate.stream()
                .filter(inserat -> filter == null || inserat.toString()
                        .toLowerCase().startsWith(filter.toLowerCase()))
                .skip(page * pageSize).limit(pageSize);
    }

    public int count() {
        return alleInserate.size();
    }

    public List<InseratDTO> fetchAll() {
        return alleInserate;
    }

}
