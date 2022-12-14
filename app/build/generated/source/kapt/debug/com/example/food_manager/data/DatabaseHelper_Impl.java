package com.example.food_manager.data;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.RoomOpenHelper.ValidationResult;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import com.example.food_manager.data.dao.ExpenseDAO;
import com.example.food_manager.data.dao.ExpenseDAO_Impl;
import com.example.food_manager.data.dao.IncomeDAO;
import com.example.food_manager.data.dao.IncomeDAO_Impl;
import com.example.food_manager.data.dao.IngredientDAO;
import com.example.food_manager.data.dao.IngredientDAO_Impl;
import com.example.food_manager.data.dao.RecipeWithIngredientsDAO;
import com.example.food_manager.data.dao.RecipeWithIngredientsDAO_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class DatabaseHelper_Impl extends DatabaseHelper {
  private volatile IngredientDAO _ingredientDAO;

  private volatile RecipeWithIngredientsDAO _recipeWithIngredientsDAO;

  private volatile ExpenseDAO _expenseDAO;

  private volatile IncomeDAO _incomeDAO;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `Recipe` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `description` TEXT NOT NULL, `cost` REAL NOT NULL, `yield` INTEGER NOT NULL, `imgUri` TEXT NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `Ingredient` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `description` TEXT NOT NULL, `quantity` INTEGER NOT NULL, `unitMeasurement` TEXT NOT NULL, `price` REAL NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `RecipeIngredientCrossRef` (`recipeID` INTEGER NOT NULL, `ingredientID` INTEGER NOT NULL, PRIMARY KEY(`recipeID`, `ingredientID`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `Expense` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `description` TEXT NOT NULL, `price` REAL NOT NULL, `imgUri` TEXT NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `Income` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `description` TEXT NOT NULL, `amount` REAL NOT NULL, `imgUri` TEXT NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '0421c47dcd9712d5dba970921a611f6f')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `Recipe`");
        _db.execSQL("DROP TABLE IF EXISTS `Ingredient`");
        _db.execSQL("DROP TABLE IF EXISTS `RecipeIngredientCrossRef`");
        _db.execSQL("DROP TABLE IF EXISTS `Expense`");
        _db.execSQL("DROP TABLE IF EXISTS `Income`");
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onDestructiveMigration(_db);
          }
        }
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      protected RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsRecipe = new HashMap<String, TableInfo.Column>(6);
        _columnsRecipe.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRecipe.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRecipe.put("description", new TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRecipe.put("cost", new TableInfo.Column("cost", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRecipe.put("yield", new TableInfo.Column("yield", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRecipe.put("imgUri", new TableInfo.Column("imgUri", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysRecipe = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesRecipe = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoRecipe = new TableInfo("Recipe", _columnsRecipe, _foreignKeysRecipe, _indicesRecipe);
        final TableInfo _existingRecipe = TableInfo.read(_db, "Recipe");
        if (! _infoRecipe.equals(_existingRecipe)) {
          return new RoomOpenHelper.ValidationResult(false, "Recipe(com.example.food_manager.domain.Recipe).\n"
                  + " Expected:\n" + _infoRecipe + "\n"
                  + " Found:\n" + _existingRecipe);
        }
        final HashMap<String, TableInfo.Column> _columnsIngredient = new HashMap<String, TableInfo.Column>(6);
        _columnsIngredient.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsIngredient.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsIngredient.put("description", new TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsIngredient.put("quantity", new TableInfo.Column("quantity", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsIngredient.put("unitMeasurement", new TableInfo.Column("unitMeasurement", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsIngredient.put("price", new TableInfo.Column("price", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysIngredient = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesIngredient = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoIngredient = new TableInfo("Ingredient", _columnsIngredient, _foreignKeysIngredient, _indicesIngredient);
        final TableInfo _existingIngredient = TableInfo.read(_db, "Ingredient");
        if (! _infoIngredient.equals(_existingIngredient)) {
          return new RoomOpenHelper.ValidationResult(false, "Ingredient(com.example.food_manager.domain.Ingredient).\n"
                  + " Expected:\n" + _infoIngredient + "\n"
                  + " Found:\n" + _existingIngredient);
        }
        final HashMap<String, TableInfo.Column> _columnsRecipeIngredientCrossRef = new HashMap<String, TableInfo.Column>(2);
        _columnsRecipeIngredientCrossRef.put("recipeID", new TableInfo.Column("recipeID", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRecipeIngredientCrossRef.put("ingredientID", new TableInfo.Column("ingredientID", "INTEGER", true, 2, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysRecipeIngredientCrossRef = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesRecipeIngredientCrossRef = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoRecipeIngredientCrossRef = new TableInfo("RecipeIngredientCrossRef", _columnsRecipeIngredientCrossRef, _foreignKeysRecipeIngredientCrossRef, _indicesRecipeIngredientCrossRef);
        final TableInfo _existingRecipeIngredientCrossRef = TableInfo.read(_db, "RecipeIngredientCrossRef");
        if (! _infoRecipeIngredientCrossRef.equals(_existingRecipeIngredientCrossRef)) {
          return new RoomOpenHelper.ValidationResult(false, "RecipeIngredientCrossRef(com.example.food_manager.domain.RecipeIngredientCrossRef).\n"
                  + " Expected:\n" + _infoRecipeIngredientCrossRef + "\n"
                  + " Found:\n" + _existingRecipeIngredientCrossRef);
        }
        final HashMap<String, TableInfo.Column> _columnsExpense = new HashMap<String, TableInfo.Column>(5);
        _columnsExpense.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExpense.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExpense.put("description", new TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExpense.put("price", new TableInfo.Column("price", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExpense.put("imgUri", new TableInfo.Column("imgUri", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysExpense = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesExpense = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoExpense = new TableInfo("Expense", _columnsExpense, _foreignKeysExpense, _indicesExpense);
        final TableInfo _existingExpense = TableInfo.read(_db, "Expense");
        if (! _infoExpense.equals(_existingExpense)) {
          return new RoomOpenHelper.ValidationResult(false, "Expense(com.example.food_manager.domain.Expense).\n"
                  + " Expected:\n" + _infoExpense + "\n"
                  + " Found:\n" + _existingExpense);
        }
        final HashMap<String, TableInfo.Column> _columnsIncome = new HashMap<String, TableInfo.Column>(5);
        _columnsIncome.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsIncome.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsIncome.put("description", new TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsIncome.put("amount", new TableInfo.Column("amount", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsIncome.put("imgUri", new TableInfo.Column("imgUri", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysIncome = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesIncome = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoIncome = new TableInfo("Income", _columnsIncome, _foreignKeysIncome, _indicesIncome);
        final TableInfo _existingIncome = TableInfo.read(_db, "Income");
        if (! _infoIncome.equals(_existingIncome)) {
          return new RoomOpenHelper.ValidationResult(false, "Income(com.example.food_manager.domain.Income).\n"
                  + " Expected:\n" + _infoIncome + "\n"
                  + " Found:\n" + _existingIncome);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "0421c47dcd9712d5dba970921a611f6f", "2e322ee318500b3da7203be5b8a15139");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "Recipe","Ingredient","RecipeIngredientCrossRef","Expense","Income");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `Recipe`");
      _db.execSQL("DELETE FROM `Ingredient`");
      _db.execSQL("DELETE FROM `RecipeIngredientCrossRef`");
      _db.execSQL("DELETE FROM `Expense`");
      _db.execSQL("DELETE FROM `Income`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(IngredientDAO.class, IngredientDAO_Impl.getRequiredConverters());
    _typeConvertersMap.put(RecipeWithIngredientsDAO.class, RecipeWithIngredientsDAO_Impl.getRequiredConverters());
    _typeConvertersMap.put(ExpenseDAO.class, ExpenseDAO_Impl.getRequiredConverters());
    _typeConvertersMap.put(IncomeDAO.class, IncomeDAO_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  public List<Migration> getAutoMigrations(
      @NonNull Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecsMap) {
    return Arrays.asList();
  }

  @Override
  public IngredientDAO ingredientDAO() {
    if (_ingredientDAO != null) {
      return _ingredientDAO;
    } else {
      synchronized(this) {
        if(_ingredientDAO == null) {
          _ingredientDAO = new IngredientDAO_Impl(this);
        }
        return _ingredientDAO;
      }
    }
  }

  @Override
  public RecipeWithIngredientsDAO recipeWithIngredientsDAO() {
    if (_recipeWithIngredientsDAO != null) {
      return _recipeWithIngredientsDAO;
    } else {
      synchronized(this) {
        if(_recipeWithIngredientsDAO == null) {
          _recipeWithIngredientsDAO = new RecipeWithIngredientsDAO_Impl(this);
        }
        return _recipeWithIngredientsDAO;
      }
    }
  }

  @Override
  public ExpenseDAO expenseDAO() {
    if (_expenseDAO != null) {
      return _expenseDAO;
    } else {
      synchronized(this) {
        if(_expenseDAO == null) {
          _expenseDAO = new ExpenseDAO_Impl(this);
        }
        return _expenseDAO;
      }
    }
  }

  @Override
  public IncomeDAO incomeDAO() {
    if (_incomeDAO != null) {
      return _incomeDAO;
    } else {
      synchronized(this) {
        if(_incomeDAO == null) {
          _incomeDAO = new IncomeDAO_Impl(this);
        }
        return _incomeDAO;
      }
    }
  }
}
