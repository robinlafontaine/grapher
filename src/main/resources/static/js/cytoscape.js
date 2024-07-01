var cy = cytoscape({
    container: document.getElementById('cy') // container to render in
});

cy.style([ // the stylesheet for the graph
    {
        selector: 'node',
        style: {
            'background-color': '#666',
            'label': 'data(name)'
        }
    },

    {
        selector: 'edge',
        style: {
            'width': 3,
            'line-color': '#ccc',
            'target-arrow-shape': 'none',
            'curve-style': 'bezier',
            'label': 'data(weight)'
        }
    }
]);

cy.zoom(0.5);
cy.layout({name: 'grid'}).run();

function addGraph(json) {
    cy.add(JSON.parse(json));
    cy.layout({name: 'grid'}).run();
}

//cy.elements.remove()
//cy.add(collection)
//cy.layout({name: 'circle'}).run()
//cy.autoungrabify(true);

