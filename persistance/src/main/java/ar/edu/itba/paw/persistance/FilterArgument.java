package ar.edu.itba.paw.persistance;

import java.util.*;

public class FilterArgument {

    private final Map<FilterTypes, Object> filters = new EnumMap<>(FilterTypes.class);//mapa <columna a filtrar,valor del "?">
    private int page=-1;
    public FilterArgument addCategory(String category) {
        return addParameter(FilterTypes.CATEGORY,category);

    }
    private FilterArgument addParameter(FilterTypes type,String value){
        if(value!=null && !value.isEmpty()){
            filters.put(type,value);
        }
        return this;
    }
    private FilterArgument addParameter(FilterTypes type,String[] value){
        if(value!=null && value.length!=0){
            filters.put(type,value);
        }
        return this;
    }

    public FilterArgument addPage(int page){
        this.page = page;
        return this;
    }

    public FilterArgument addLocation(String[] location) {
        return addParameter(FilterTypes.LOCATION,location);
    }

    public FilterArgument addSearch(String search) {
        if(search!=null && !search.isEmpty()){
            filters.put(FilterTypes.SERVICE_SEARCH,search.replace("%","\\%"));
        }
        return this;
    }

    public String formSqlSentence() {
        StringBuilder sql = new StringBuilder("where true "); //(p ^ 1) === p
        for (Map.Entry<FilterTypes, Object> entries : filters.entrySet()) {
                sql.append("and ").append(entries.getKey()).append(" ");
        }
        if(page!=-1){
            sql.append(" ORDER BY id ASC OFFSET ? LIMIT 10");
        }
        return sql.toString();
    }

    public List<Object> getValues() {
        List<Object> values= new ArrayList<>(filters.values());
        if(page!=-1){
            values.add(page*10);
        }
        return values;
    }

        private enum FilterTypes {
            CATEGORY("category = ? "),
            LOCATION("serviceid in (select neighbourhoodservices where neighbourhood = any (?) )"),
            SERVICE_SEARCH("lower(servicename) like concat('%',lower(?),'%')");

            private final String value; //valores a ser filtrados/buscados en SQL

            FilterTypes(String value) {
                this.value = value;
            }

            @Override
            public String toString() {
                return value;
            }

        }
    }
