// The routes object is identical to the props Route:
type router = {
  path: string;
  strict?: boolean;
  exact?: boolean;
}

export const
  main: router = { path: '/', exact: true },
  bookForm: router = { path: '/books/:id?' };
