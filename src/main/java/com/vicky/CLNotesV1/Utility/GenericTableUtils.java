//package com.vicky.CLNotesV1.Utility;
//
//import org.springframework.shell.table.*;
//
//import java.lang.reflect.Field;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
//public class GenericTableUtils {
//
//    private GenericTableUtils() {}
//
//    public static Table toTable(List<?> dtoList) {
//        if (dtoList == null || dtoList.isEmpty()) {
//            return emptyTable();
//        }
//
//        Object first = dtoList.get(0);
//        Field[] fields = first.getClass().getDeclaredFields();
//
//        String[] headers = extractHeaders(fields);
//        String[][] rows = extractRows(dtoList, fields);
//
//        return build(headers, rows);
//    }
//
//    public static Table toTable(Object dto) {
//        if (dto == null) {
//            return emptyTable();
//        }
//        List<Object> list = new ArrayList<>();
//        list.add(dto);
//        return toTable(list);
//    }
//
//    private static Table build(String[] headers, String[][] rows) {
//        String[][] data = new String[rows.length + 1][headers.length];
//
//        data[0] = headers;
//        System.arraycopy(rows, 0, data, 1, rows.length);
//
//        TableModel model = new ArrayTableModel(data);
//        TableBuilder builder = new TableBuilder(model);
//        builder.addFullBorder(BorderStyle.oldschool);
//
//        return builder.build();
//    }
//
//    private static String[] extractHeaders(Field[] fields) {
//        String[] headers = new String[fields.length];
//        for (int i = 0; i < fields.length; i++) {
//            headers[i] = fields[i].getName();
//        }
//        return headers;
//    }
//
//    private static String[][] extractRows(List<?> dtoList, Field[] fields) {
//        String[][] rows = new String[dtoList.size()][fields.length];
//
//        for (int i = 0; i < dtoList.size(); i++) {
//            Object dto = dtoList.get(i);
//
//            for (int j = 0; j < fields.length; j++) {
//                fields[j].setAccessible(true);
//                try {
//                    Object value = fields[j].get(dto);
//                    rows[i][j] = formatValue(value);
//                } catch (IllegalAccessException e) {
//                    rows[i][j] = "ERROR";
//                }
//            }
//        }
//
//        return rows;
//    }
//
//    private static String formatValue(Object value) {
//        if (value == null) {
//            return "";
//        }
//
//        if (value instanceof List<?> list) {
//            return list.stream()
//                    .map(GenericTableUtils::extractReadableValue)
//                    .collect(Collectors.joining(", "));
//        }
//
//        return value.toString();
//    }
//
//    private static String extractReadableValue(Object item) {
//        if (item == null) return "";
//
//        String[] preferredFields = { "title", "tagName", "name" };
//
//        for (String fieldName : preferredFields) {
//            try {
//                Field field = item.getClass().getDeclaredField(fieldName);
//                field.setAccessible(true);
//                Object val = field.get(item);
//                if (val != null) {
//                    return val.toString();
//                }
//            } catch (NoSuchFieldException | IllegalAccessException ignored) {}
//        }
//
//        return item.getClass().getSimpleName();
//    }
//
//    private static Table emptyTable() {
//        String[][] data = { { "NO DATA" } };
//        TableModel model = new ArrayTableModel(data);
//        return new TableBuilder(model).build();
//    }
//}
