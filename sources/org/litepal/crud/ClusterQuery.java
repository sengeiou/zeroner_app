package org.litepal.crud;

import java.util.List;
import org.litepal.LitePal;
import org.litepal.crud.async.AverageExecutor;
import org.litepal.crud.async.CountExecutor;
import org.litepal.crud.async.FindExecutor;
import org.litepal.crud.async.FindMultiExecutor;
import org.litepal.tablemanager.Connector;
import org.litepal.util.BaseUtility;
import org.litepal.util.DBUtility;

public class ClusterQuery {
    String[] mColumns;
    String[] mConditions;
    String mLimit;
    String mOffset;
    String mOrderBy;

    ClusterQuery() {
    }

    public ClusterQuery select(String... columns) {
        this.mColumns = columns;
        return this;
    }

    public ClusterQuery where(String... conditions) {
        this.mConditions = conditions;
        return this;
    }

    public ClusterQuery order(String column) {
        this.mOrderBy = column;
        return this;
    }

    public ClusterQuery limit(int value) {
        this.mLimit = String.valueOf(value);
        return this;
    }

    public ClusterQuery offset(int value) {
        this.mOffset = String.valueOf(value);
        return this;
    }

    public <T> List<T> find(Class<T> modelClass) {
        return find(modelClass, false);
    }

    public <T> FindMultiExecutor findAsync(Class<T> modelClass) {
        return findAsync(modelClass, false);
    }

    public synchronized <T> List<T> find(Class<T> modelClass, boolean isEager) {
        QueryHandler queryHandler;
        String limit;
        queryHandler = new QueryHandler(Connector.getDatabase());
        if (this.mOffset == null) {
            limit = this.mLimit;
        } else {
            if (this.mLimit == null) {
                this.mLimit = "0";
            }
            limit = this.mOffset + "," + this.mLimit;
        }
        return queryHandler.onFind(modelClass, this.mColumns, this.mConditions, this.mOrderBy, limit, isEager);
    }

    public <T> FindMultiExecutor findAsync(final Class<T> modelClass, final boolean isEager) {
        final FindMultiExecutor executor = new FindMultiExecutor();
        executor.submit(new Runnable() {
            public void run() {
                synchronized (DataSupport.class) {
                    final List<T> t = ClusterQuery.this.find(modelClass, isEager);
                    if (executor.getListener() != null) {
                        LitePal.getHandler().post(new Runnable() {
                            public void run() {
                                executor.getListener().onFinish(t);
                            }
                        });
                    }
                }
            }
        });
        return executor;
    }

    public <T> T findFirst(Class<T> modelClass) {
        return findFirst(modelClass, false);
    }

    public <T> FindExecutor findFirstAsync(Class<T> modelClass) {
        return findFirstAsync(modelClass, false);
    }

    public <T> T findFirst(Class<T> modelClass, boolean isEager) {
        List<T> list = find(modelClass, isEager);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    public <T> FindExecutor findFirstAsync(final Class<T> modelClass, final boolean isEager) {
        final FindExecutor executor = new FindExecutor();
        executor.submit(new Runnable() {
            public void run() {
                synchronized (DataSupport.class) {
                    final T t = ClusterQuery.this.findFirst(modelClass, isEager);
                    if (executor.getListener() != null) {
                        LitePal.getHandler().post(new Runnable() {
                            public void run() {
                                executor.getListener().onFinish(t);
                            }
                        });
                    }
                }
            }
        });
        return executor;
    }

    public <T> T findLast(Class<T> modelClass) {
        return findLast(modelClass, false);
    }

    public <T> FindExecutor findLastAsync(Class<T> modelClass) {
        return findLastAsync(modelClass, false);
    }

    public <T> T findLast(Class<T> modelClass, boolean isEager) {
        List<T> list = find(modelClass, isEager);
        int size = list.size();
        if (size > 0) {
            return list.get(size - 1);
        }
        return null;
    }

    public <T> FindExecutor findLastAsync(final Class<T> modelClass, final boolean isEager) {
        final FindExecutor executor = new FindExecutor();
        executor.submit(new Runnable() {
            public void run() {
                synchronized (DataSupport.class) {
                    final T t = ClusterQuery.this.findLast(modelClass, isEager);
                    if (executor.getListener() != null) {
                        LitePal.getHandler().post(new Runnable() {
                            public void run() {
                                executor.getListener().onFinish(t);
                            }
                        });
                    }
                }
            }
        });
        return executor;
    }

    public synchronized int count(Class<?> modelClass) {
        return count(BaseUtility.changeCase(modelClass.getSimpleName()));
    }

    public CountExecutor countAsync(Class<?> modelClass) {
        return countAsync(BaseUtility.changeCase(DBUtility.getTableNameByClassName(modelClass.getName())));
    }

    public synchronized int count(String tableName) {
        return new QueryHandler(Connector.getDatabase()).onCount(tableName, this.mConditions);
    }

    public CountExecutor countAsync(final String tableName) {
        final CountExecutor executor = new CountExecutor();
        executor.submit(new Runnable() {
            public void run() {
                synchronized (DataSupport.class) {
                    final int count = ClusterQuery.this.count(tableName);
                    if (executor.getListener() != null) {
                        LitePal.getHandler().post(new Runnable() {
                            public void run() {
                                executor.getListener().onFinish(count);
                            }
                        });
                    }
                }
            }
        });
        return executor;
    }

    public synchronized double average(Class<?> modelClass, String column) {
        return average(BaseUtility.changeCase(modelClass.getSimpleName()), column);
    }

    public AverageExecutor averageAsync(Class<?> modelClass, String column) {
        return averageAsync(BaseUtility.changeCase(DBUtility.getTableNameByClassName(modelClass.getName())), column);
    }

    public synchronized double average(String tableName, String column) {
        return new QueryHandler(Connector.getDatabase()).onAverage(tableName, column, this.mConditions);
    }

    public AverageExecutor averageAsync(final String tableName, final String column) {
        final AverageExecutor executor = new AverageExecutor();
        executor.submit(new Runnable() {
            public void run() {
                synchronized (DataSupport.class) {
                    final double average = ClusterQuery.this.average(tableName, column);
                    if (executor.getListener() != null) {
                        LitePal.getHandler().post(new Runnable() {
                            public void run() {
                                executor.getListener().onFinish(average);
                            }
                        });
                    }
                }
            }
        });
        return executor;
    }

    public synchronized <T> T max(Class<?> modelClass, String columnName, Class<T> columnType) {
        return max(BaseUtility.changeCase(modelClass.getSimpleName()), columnName, columnType);
    }

    public <T> FindExecutor maxAsync(Class<?> modelClass, String columnName, Class<T> columnType) {
        return maxAsync(BaseUtility.changeCase(DBUtility.getTableNameByClassName(modelClass.getName())), columnName, columnType);
    }

    public synchronized <T> T max(String tableName, String columnName, Class<T> columnType) {
        return new QueryHandler(Connector.getDatabase()).onMax(tableName, columnName, this.mConditions, columnType);
    }

    public <T> FindExecutor maxAsync(String tableName, String columnName, Class<T> columnType) {
        final FindExecutor executor = new FindExecutor();
        final String str = tableName;
        final String str2 = columnName;
        final Class<T> cls = columnType;
        executor.submit(new Runnable() {
            public void run() {
                synchronized (DataSupport.class) {
                    final T t = ClusterQuery.this.max(str, str2, cls);
                    if (executor.getListener() != null) {
                        LitePal.getHandler().post(new Runnable() {
                            public void run() {
                                executor.getListener().onFinish(t);
                            }
                        });
                    }
                }
            }
        });
        return executor;
    }

    public synchronized <T> T min(Class<?> modelClass, String columnName, Class<T> columnType) {
        return min(BaseUtility.changeCase(modelClass.getSimpleName()), columnName, columnType);
    }

    public <T> FindExecutor minAsync(Class<?> modelClass, String columnName, Class<T> columnType) {
        return minAsync(BaseUtility.changeCase(DBUtility.getTableNameByClassName(modelClass.getName())), columnName, columnType);
    }

    public synchronized <T> T min(String tableName, String columnName, Class<T> columnType) {
        return new QueryHandler(Connector.getDatabase()).onMin(tableName, columnName, this.mConditions, columnType);
    }

    public <T> FindExecutor minAsync(String tableName, String columnName, Class<T> columnType) {
        final FindExecutor executor = new FindExecutor();
        final String str = tableName;
        final String str2 = columnName;
        final Class<T> cls = columnType;
        executor.submit(new Runnable() {
            public void run() {
                synchronized (DataSupport.class) {
                    final T t = ClusterQuery.this.min(str, str2, cls);
                    if (executor.getListener() != null) {
                        LitePal.getHandler().post(new Runnable() {
                            public void run() {
                                executor.getListener().onFinish(t);
                            }
                        });
                    }
                }
            }
        });
        return executor;
    }

    public synchronized <T> T sum(Class<?> modelClass, String columnName, Class<T> columnType) {
        return sum(BaseUtility.changeCase(modelClass.getSimpleName()), columnName, columnType);
    }

    public <T> FindExecutor sumAsync(Class<?> modelClass, String columnName, Class<T> columnType) {
        return sumAsync(BaseUtility.changeCase(DBUtility.getTableNameByClassName(modelClass.getName())), columnName, columnType);
    }

    public synchronized <T> T sum(String tableName, String columnName, Class<T> columnType) {
        return new QueryHandler(Connector.getDatabase()).onSum(tableName, columnName, this.mConditions, columnType);
    }

    public <T> FindExecutor sumAsync(String tableName, String columnName, Class<T> columnType) {
        final FindExecutor executor = new FindExecutor();
        final String str = tableName;
        final String str2 = columnName;
        final Class<T> cls = columnType;
        executor.submit(new Runnable() {
            public void run() {
                synchronized (DataSupport.class) {
                    final T t = ClusterQuery.this.sum(str, str2, cls);
                    if (executor.getListener() != null) {
                        LitePal.getHandler().post(new Runnable() {
                            public void run() {
                                executor.getListener().onFinish(t);
                            }
                        });
                    }
                }
            }
        });
        return executor;
    }
}
