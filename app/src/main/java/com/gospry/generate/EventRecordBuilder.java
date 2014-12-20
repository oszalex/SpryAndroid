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
// This file is generated. If you want to save you some time: !!!DO NOT MODIFY!!!
package com.gospry.generate;

import android.database.sqlite.SQLiteDatabase;

import at.pasra.record.RecordBuilder;

public class EventRecordBuilder extends RecordBuilder<Event> {
    public EventRecordBuilder(SQLiteDatabase db) {
        super("events", new String[]{"user", "start_time", "remote_id", "description", "duration", "max_attending", "min_attending", "price", "is_public", "accept_state", "_id"}, db);
    }

    @Override
    public java.util.List<Event> all(android.database.Cursor c) {
        java.util.List<Event> list = new java.util.ArrayList<Event>();
        while (c.moveToNext()) {
            list.add(Event.fromCursor(c));
        }
        return list;
    }

    @Override
    public Event first(android.database.Cursor c) {
        if (c.moveToFirst()) {
            Event record = Event.fromCursor(c);
            c.close();
            return record;
        }
        c.close();
        return null;
    }
}
