import { Test, TestingModule } from '@nestjs/testing'

import { ProfilesController } from '@profiles/profiles.controller'
import { ProfilesService } from '@profiles/profiles.service'

describe('ProfilesController', () => {
  let controller: ProfilesController

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      controllers: [ProfilesController],
      providers: [ProfilesService],
    }).compile()

    controller = module.get<ProfilesController>(ProfilesController)
  })

  it('should be defined', () => {
    expect(controller).toBeDefined()
  })
})
