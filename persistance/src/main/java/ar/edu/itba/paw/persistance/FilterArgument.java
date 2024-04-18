package ar.edu.itba.paw.persistance;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class FilterArgument {

    private final Map<FilterTypes, Object> filters = new EnumMap<>(FilterTypes.class);//mapa <columna a filtrar,valor del "?">

    public FilterArgument addCategory(String category) {
        return addParameter(FilterTypes.CATEGORY,category);

    }
    private FilterArgument addParameter(FilterTypes type,String value){
        if(value!=null && !value.isEmpty()){
            filters.put(type,value);
        }
        return this;
    }

    public FilterArgument addLocation(String location) {
        return addParameter(FilterTypes.LOCATION,location);
    }

    public FilterArgument addSearch(String search) {
        if(search!=null && !search.isEmpty()){
            filters.put(FilterTypes.SEARCH,search.replace("%","\\%"));
        }
        return this;
    }

    public String formSqlSentence() {
        StringBuilder sql = new StringBuilder("where true "); //(p ^ 1) === p
        for (Map.Entry<FilterTypes, Object> entries : filters.entrySet()) {
                sql.append("and ").append(entries.getKey()).append(" ");
        }
        return sql.toString();
    }

    public List<Object> getValues() {
        return Arrays.asList(filters.values().toArray());
    }

        private enum FilterTypes {
            CATEGORY("category = ? "),
            LOCATION("location = ? "),
            SEARCH("servicename like ? ");

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
