//
//  HideAbleSearchTextField.swift
//  iosApp
//
//  Created by Mostafa Hosseini on 1/30/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct HideAbleSearchTextField<Destination: View>: View {
    // defining this struct properties
    var onSearchToggle: () -> Void
    // determine from outside where to go (in this case, check mark)
    var destinationProvider: () -> Destination
    var isSearchActive: Bool
    // @Binding: view can change this value
    @Binding var searchText: String

    var body: some View {
        // HStack -> Row, VStack -> Column
        HStack {
            TextField("Search...", text: $searchText)
                .textFieldStyle(.plain)
                .opacity(isSearchActive ? 1 : 0)
            if !isSearchActive {
                // occupy all remaining space
                Spacer()
            }
            Button(action: onSearchToggle) {
                Image(systemName: isSearchActive ? "xmark" : "magnifyingglass")
                    .foregroundColor(.black)
            }
            NavigationLink(destination: destinationProvider) {
                Image(systemName: "plus")
                    .foregroundColor(.black)
            }
        }
    }
}

struct HideAbleSearchTextField_Previews: PreviewProvider {
    static var previews: some View {
        HideAbleSearchTextField(
            onSearchToggle: {},
            destinationProvider: { EmptyView() },
            isSearchActive: true,
            searchText: .constant("Youtube")
        )
    }
}
