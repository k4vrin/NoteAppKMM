//
//  NoteListViewModel.swift
//  iosApp
//
//  Created by Mostafa Hosseini on 1/30/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import shared

// With extension we can say that the content of this extension will be created and used only in the object that being extended
// MainActor -> Run on main thread
extension NoteListScreen {
    @MainActor class NoteListViewModel: ObservableObject {
        private var noteDataSource: NoteDataSource?
        
        private let searchNotes = SearchNotes()
        
        // list
        private var notes = [Note]()
        // public get private set
        // With @Published the ui knows when to redraw (state in compose)
        @Published private(set) var filteredNotes = [Note]()
        @Published var searchText = "" {
            // After searchText has been set
            didSet {
                filteredNotes = searchNotes.execute(notes: notes, query: searchText)
            }
        }
        
        @Published private(set) var isSearchActive = false
        
        // constructor
        init(noteDataSource: NoteDataSource? = nil) {
            self.noteDataSource = noteDataSource
        }
        
        func loadNotes() {
            noteDataSource?.getAllNotes(completionHandler: { notes, _ in
                self.notes = notes ?? []
                self.filteredNotes = self.notes
            })
        }
        
        func deleteNoteById(id: Int64?) {
            if id != nil {
                noteDataSource?.deleteNoteById(id: id!, completionHandler: { _ in
                    self.loadNotes()
                })
            }
        }
        
        func toggleIsSearchActive() {
            isSearchActive = !isSearchActive
            if !isSearchActive {
                searchText = ""
            }
        }
        
        func setNoteDataSource(noteDataSource: NoteDataSource) {
            self.noteDataSource = noteDataSource
//            self.noteDataSource?.insertNote(
//                note: Note(
//                    id: nil,
//                    title: "Note title",
//                    content: "Hello",
//                    colorHex: 0xFF2355,
//                    created: DateTimeUtil().now()),
//                completionHandler: { _ in })
        }
    }
}
