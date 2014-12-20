/* Copyright (c) 2013, Richard Plangger <rich@pasra.at> All rights reserved.
 *
 * Android Record version 0.1.4 generated this file. For more
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
package com.gospry.generate;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class AccountRecord {
    private final java.util.Map<Long, Account> primaryKeyCache = new java.util.HashMap<Long, Account>();

    public void clearCache() {
        primaryKeyCache.clear();
    }

    public void save(SQLiteDatabase db, AbstractAccount record) {
        if (record.getId() == null) {
            insert(db, record);
        } else {
            update(db, record);
        }
    }

    public void insert(SQLiteDatabase db, AbstractAccount record) {
        ContentValues values = new ContentValues(2);
        values.put("number", record.getNumber());
        values.put("secret", record.getSecret());
        long id = db.insert("accounts", null, values);
        record.setId(id);
        primaryKeyCache.put(id, (Account) record);
    }

    public Account load(SQLiteDatabase db, long id) {
        Account cached = primaryKeyCache.get(id);
        if (cached != null) {
            return cached;
        }
        Cursor c = db.rawQuery("select number, secret, _id from accounts where _id = ?;", new String[]{Long.toString(id)});
        if (c.moveToFirst()) {
            Account record = new Account();
            record.setNumber(c.getString(c.getColumnIndex("number")));
            record.setSecret(c.getString(c.getColumnIndex("secret")));
            record.setId(c.getLong(c.getColumnIndex("_id")));
            primaryKeyCache.put(id, record);
            return record;
        }
        return null;
    }

    public void delete(SQLiteDatabase db, long id) {
        db.execSQL("delete from accounts where  _id = ?;", new String[]{Long.toString(id)});
        primaryKeyCache.remove(id);
    }

    public void update(SQLiteDatabase db, AbstractAccount record) {
        ContentValues values = new ContentValues(2);
        values.put("number", record.getNumber());
        values.put("secret", record.getSecret());
        long id = record.getId();
        db.update("accounts", values, "_id = ?", new String[]{Long.toString(id)});
    }
}
