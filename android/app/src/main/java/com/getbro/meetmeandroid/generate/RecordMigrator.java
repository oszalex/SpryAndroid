/* Copyright (c) 2013, Richard Plangger <rich@pasra.at> All rights reserved.
 *
 * Android Record version 0.1.0 generated this file. For more
 * information see http://record.pasra.at/
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS
 * OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY
 * AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 *
 * This copyright notice must not be modified or deleted.
 */
// This file is generated. If you want to save you some time: !!!DO NOT MODIFY!!!
package com.getbro.meetmeandroid.generate;

import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.content.ContentValues;
import at.pasra.record.Migrator;

public class RecordMigrator implements Migrator{
    public static final long MIGRATION_LEVEL = 20141113125812L;
    
    private final SQLiteDatabase db;
    public RecordMigrator(SQLiteDatabase db){
        this.db = db;
        this.tryCreateVersioningTable();
    }
    private void tryCreateVersioningTable(){
        db.execSQL("create table if not exists android_record_configs (_id integer primary key, key text unique not null, value text);");
    }
    public long getCurrentMigrationLevel(){
        Cursor c = db.rawQuery(
                    "select key, value from android_record_configs where key = ? order by value desc limit 1;",
                    new String[] { "version" } );
        if (c.moveToNext()){
            long version = Long.parseLong(c.getString(1));
            c.close();
            return version;
        }
        return 0;
    }
    public long getLatestMigrationLevel(){
        return MIGRATION_LEVEL;
    }
    @Override
    public void migrate(){
        migrate(getCurrentMigrationLevel(), MIGRATION_LEVEL);
    }
    @Override
    public void migrate(long currentVersion, long targetVersion){
        db.execSQL("insert or replace into android_record_configs (key,value) values ('generator_version','0.1.0')");
        if (currentVersion < targetVersion && currentVersion < 20141113125812L){
            db.execSQL("create table accounts (number text , secret text , _id integer primary key);");
            db.execSQL("create table events (user text , start_time integer , remote_id integer , description text , duration integer , max_attending integer , min_attending integer , price long , is_public integer , accept_state text , _id integer primary key);");
            db.execSQL("create table keywords (text text , event_id integer , _id integer primary key);");
            currentVersion = 20141113125812L;
        }

        db.execSQL("insert or replace into android_record_configs (key,value) values (?,?)", new Object[] { "version", new Long(currentVersion) });
    }
}
