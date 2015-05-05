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

public class EventRecord {
    private final java.util.Map<Long, Event> primaryKeyCache = new java.util.HashMap<Long, Event>();

    public void clearCache() {
        primaryKeyCache.clear();
    }

    public void save(SQLiteDatabase db, AbstractEvent record) {
        if (record.getId() == null) {
            insert(db, record);
        } else {
            update(db, record);
        }
    }

    public void insert(SQLiteDatabase db, AbstractEvent record) {
        ContentValues values = new ContentValues(12);
        values.put("user", record.getUser());
        values.put("start_time", record.getStartTime());
        values.put("remote_id", record.getRemoteId());
        values.put("description", record.getDescription());
        values.put("duration", record.getDuration());
        values.put("max_attending", record.getMaxAttending());
        values.put("min_attending", record.getMinAttending());
        values.put("price", Double.doubleToLongBits(record.getPrice()));
        values.put("is_public", record.getIsPublic());
        values.put("accept_state", record.getAcceptState());
        values.put("location", record.getLocation());
        values.put("invitationstatus", record.getInvitationstatus());
        long id = db.insert("events", null, values);
        record.setId(id);
        primaryKeyCache.put(id, (Event) record);
    }

    public Event load(SQLiteDatabase db, long id) {
        Event cached = primaryKeyCache.get(id);
        if (cached != null) {
            return cached;
        }
        Cursor c = db.rawQuery("select user, start_time, remote_id, description, duration, max_attending, min_attending, price, is_public, accept_state, _id, location, invitationstatus from events where _id = ?;", new String[]{Long.toString(id)});
        if (c.moveToFirst()) {
            Event record = new Event();
            record.setUser(c.getString(c.getColumnIndex("user")));
            record.setStartTime(c.getLong(c.getColumnIndex("start_time")));
            record.setRemoteId(c.getLong(c.getColumnIndex("remote_id")));
            record.setDescription(c.getString(c.getColumnIndex("description")));
            record.setDuration(c.getInt(c.getColumnIndex("duration")));
            record.setMaxAttending(c.getInt(c.getColumnIndex("max_attending")));
            record.setMinAttending(c.getInt(c.getColumnIndex("min_attending")));
            record.setPrice(Double.longBitsToDouble(c.getLong(c.getColumnIndex("price"))));
            record.setIsPublic((c.getInt(c.getColumnIndex("is_public")) != 0));
            record.setAcceptState(c.getString(c.getColumnIndex("accept_state")));
            record.setId(c.getLong(c.getColumnIndex("_id")));
            record.setLocation(c.getString(c.getColumnIndex("location")));
            record.setInvitationstatus(c.getString(c.getColumnIndex("invitationstatus")));
            primaryKeyCache.put(id, record);
            return record;
        }
        return null;
    }

    public void delete(SQLiteDatabase db, long id) {
        db.execSQL("delete from events where  _id = ?;", new String[]{Long.toString(id)});
        primaryKeyCache.remove(id);
    }

    public void update(SQLiteDatabase db, AbstractEvent record) {
        ContentValues values = new ContentValues(12);
        values.put("user", record.getUser());
        values.put("start_time", record.getStartTime());
        values.put("remote_id", record.getRemoteId());
        values.put("description", record.getDescription());
        values.put("duration", record.getDuration());
        values.put("max_attending", record.getMaxAttending());
        values.put("min_attending", record.getMinAttending());
        values.put("price", Double.doubleToLongBits(record.getPrice()));
        values.put("is_public", record.getIsPublic());
        values.put("accept_state", record.getAcceptState());
        values.put("location", record.getLocation());
        values.put("invitationstatus", record.getInvitationstatus());
        long id = record.getId();
        db.update("events", values, "_id = ?", new String[]{Long.toString(id)});
    }
}
