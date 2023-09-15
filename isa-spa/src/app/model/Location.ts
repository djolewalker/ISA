export type Coordinates = [number, number][];

export type ISALocation = {
  id: number;
  longitude: number;
  latitude: number;
  name: string;
};

export type DriverLocation = {
  id: number;
  occupied: boolean;
} & ISALocation;

export type DriverStatus = {
  active: boolean;
};
