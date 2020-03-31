// actionCreator :: type -> (data, filter | undefined) -> {
//   type: 'COMMAND' | 'DOCUMENT' | 'EVENT' | 'SIDE_EFFECT',
//   payload: data,
//   meta: { type: type }
// }

export const createActionFunction = type => [
  payload => payload,
  () => ({ type })
];
